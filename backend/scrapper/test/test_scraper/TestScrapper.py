import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '../..')))

import unittest 
from unittest.mock import patch, Mock
from scraper import MovieLinkScraper

class TestScrapper(unittest.TestCase):

    def setUp(self):
        self.scraper = MovieLinkScraper()
        self.list_of_mixed_links = b'''
            <div id="mw-content-text">
                <a href="/wiki/The_Broadway_Melody" title="The Broadway Melody">The Broadway Melody</a>
                <a href="/wiki/Avatar_(2009_film)" title="Avatar">Avatar</a>
                <a href="/wiki/Titanic_(1997_film)" title="Titanic">Titanic</a>
                <span>Some random text</span>
                <a href="/wiki/Non-Movie_Link" title="Non Movie Link">Non Movie</a>
                <a title="Link without href">No URL</a>
            </div>
        '''

        self.table_with_title_column = b'''
            <html>
            <body>
                <table>
                    <tr><th>Title</th><th>Year</th></tr>
                    <tr><td><a href="/wiki/Film1">Film 1</a></td><td>2020</td></tr>
                    <tr><td><a href="/wiki/Film2">Film 2</a></td><td>2021</td></tr>
                </table>
                <table>
                    <tr><th>Director</th><th>Title</th></tr>
                    <tr><td>Director 1</td><td><a href="/wiki/Film3">Film 3</a></td></tr>
                    <tr><td>Director 2</td><td><a href="/wiki/Film4">Film 4</a></td></tr>
                </table>
            </body>
            </html>
        '''

        self.table_without_title_column = b'''
            <html>
            <body>
                <table>
                    <tr><th>Director</th><th>Year</th></tr>
                    <tr><td>Director 1</td><td>2020</td></tr>
                    <tr><td>Director 2</td><td>2021</td></tr>
                </table>
            </body>
            </html>
        '''

        self.list_of_movie_links_1 = b'''
            <div id="mw-content-text">
                <a href="/wiki/Movie_1" title="Movie 1">Movie 1</a>
                <a href="/wiki/Movie_2" title="Movie 2">Movie 2</a>
            </div>
        '''

        self.list_of_movie_links_2 = b'''
            <div id="mw-content-text">
                <a href="/wiki/Movie_3" title="Movie 3">Movie 3</a>
                <a href="/wiki/Movie_4" title="Movie 4">Movie 4</a>
            </div>
        '''

        self.movie_lists_urls = [
            "https://en.wikipedia.org/wiki/List_of_highest-grossing_films",
            "https://en.wikipedia.org/wiki/List_of_films_considered_the_worst"
        ]

    def mock_response(self, status_code, content):
        """
        Helper function to return a mock response with params
        """
        mock_response = Mock()
        mock_response.status_code = status_code
        mock_response.content = content
        return mock_response

    @patch('requests.get')
    def test_fetch_and_extract(self, mock_get):
        #ARRANGE
        mock_get.return_value = self.mock_response(200, self.list_of_mixed_links)
        url = "https:/en.wikipedia.org/wiki/List_of_highest-grossing_films"

        #ACT
        self.scraper.fetch_and_extract(url)

        #ASSERT
        self.assertEqual(len(self.scraper.movie_links), 3)

        self.assertEqual(self.scraper.movie_links[0]['title'], 'The Broadway Melody')
        self.assertEqual(self.scraper.movie_links[0]['url'], 'https://en.wikipedia.org/wiki/The_Broadway_Melody')

        self.assertEqual(self.scraper.movie_links[1]['title'], 'Avatar')
        self.assertEqual(self.scraper.movie_links[1]['url'], 'https://en.wikipedia.org/wiki/Avatar_(2009_film)')

        self.assertEqual(self.scraper.movie_links[2]['title'], 'Titanic')
        self.assertEqual(self.scraper.movie_links[2]['url'], 'https://en.wikipedia.org/wiki/Titanic_(1997_film)')

        print("Test passed: Fetch and extract correctly")

    @patch('requests.get')
    def test_check_title_exists_and_extract_table(self, mock_get):
        #ARRANGE
        mock_get.return_value = self.mock_response(200, self.table_with_title_column)
        url = "https://en.wikipedia.org/wiki/Test_Page"
        wikibase = "https://en.wikipedia.org"
        keyword = "Title"

        #ACT
        result = self.scraper.check_title_exists_and_extract_table(url, wikibase, keyword)
        expected_links = {
            'https://en.wikipedia.org/wiki/Film1',
            'https://en.wikipedia.org/wiki/Film2',
            'https://en.wikipedia.org/wiki/Film3',
            'https://en.wikipedia.org/wiki/Film4'
        }

        #ASSERT
        self.assertEqual(result, expected_links)
        print("Test passed: check_title_exists_and_extract_table")

    @patch('requests.get')
    def test_check_title_exists_no_title_column(self, mock_get):
        #ARRANGE
        mock_get.return_value = self.mock_response(200, self.table_without_title_column)
        url = "https://en.wikipedia.org/wiki/Test_Page_No_Title"
        wikibase = "https://en.wikipedia.org"
        keyword = "Title"

        #ACT
        result = self.scraper.check_title_exists_and_extract_table(url, wikibase, keyword)

        #ASSERT
        self.assertEqual(result, set())
        print("Test passed: No title column found")

    @patch('requests.get')
    def test_run(self, mock_get):
        #ARRANGE
        mock_get.side_effect = [
            self.mock_response(200, self.list_of_movie_links_1),
            self.mock_response(200, self.list_of_movie_links_2)
        ]

        #ACT
        self.scraper.run(self.movie_lists_urls)

        #ASSERT
        self.assertEqual(len(self.scraper.movie_links), 4)  
        self.assertEqual(self.scraper.movie_links[0]['title'], 'Movie 1')
        self.assertEqual(self.scraper.movie_links[1]['title'], 'Movie 2')
        self.assertEqual(self.scraper.movie_links[2]['title'], 'Movie 3')
        self.assertEqual(self.scraper.movie_links[3]['title'], 'Movie 4')
        print("Test passed: run method extracted movie links successfully.")

if __name__ == "__main__":
    unittest.main()