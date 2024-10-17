import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

import unittest 
from unittest.mock import patch, Mock
from scraper import MovieLinkScraper

class TestScrapper(unittest.TestCase):

    @patch('requests.get')
    def test_fetch_and_extract(self, mock_get):
        # Mock a successful response for fetch_and_extract
        scraper = MovieLinkScraper()
        mock_response = Mock()

        # Simulate a subset of a Wikipedia page with mixed content
        mock_response.content = b'''
            <div id="mw-content-text">
                <a href="/wiki/The_Broadway_Melody" title="The Broadway Melody">The Broadway Melody</a>
                <a href="/wiki/Avatar_(2009_film)" title="Avatar">Avatar</a>
                <a href="/wiki/Titanic_(1997_film)" title="Titanic">Titanic</a>
                <span>Some random text</span>
                <a href="/wiki/Non-Movie_Link" title="Non Movie Link">Non Movie</a>
                <a title="Link without href">No URL</a>
            </div>
        '''
        mock_response.status_code = 200
        mock_get.return_value = mock_response

        # Simulate fetching and extracting links from the mocked page
        url = "https://en.wikipedia.org/wiki/List_of_highest-grossing_films"
        scraper.fetch_and_extract(url)

        # Verify that only valid movie links are extracted
        self.assertEqual(len(scraper.movie_links), 3)

        # Verify each link title and URL
        self.assertEqual(scraper.movie_links[0]['title'], 'The Broadway Melody')
        self.assertEqual(scraper.movie_links[0]['url'], 'https://en.wikipedia.org/wiki/The_Broadway_Melody')

        self.assertEqual(scraper.movie_links[1]['title'], 'Avatar')
        self.assertEqual(scraper.movie_links[1]['url'], 'https://en.wikipedia.org/wiki/Avatar_(2009_film)')

        self.assertEqual(scraper.movie_links[2]['title'], 'Titanic')
        self.assertEqual(scraper.movie_links[2]['url'], 'https://en.wikipedia.org/wiki/Titanic_(1997_film)')

        # Ensure no links without URLs or invalid movie links are extracted
        print("Test passed: Fetch and extract correctly")

    
    @patch('requests.get')
    def test_check_title_exists_and_extract_table(self, mock_get):
        # Initialize the scraper
        scraper = MovieLinkScraper()
        
        # Mock the response from requests.get to simulate a webpage with tables
        mock_response = Mock()

        # Sample HTML content with tables and a "Title" column
        mock_response.content = b'''
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
        mock_response.status_code = 200
        mock_get.return_value = mock_response

        # Provide the URL and keyword for the test
        url = "https://en.wikipedia.org/wiki/Test_Page"
        wikibase = "https://en.wikipedia.org"
        keyword = "Title"

        # Call the function to test
        result = scraper.check_title_exists_and_extract_table(url, wikibase, keyword)

        # Expected result - The set of extracted and formatted links
        expected_links = {
            'https://en.wikipedia.org/wiki/Film1',
            'https://en.wikipedia.org/wiki/Film2',
            'https://en.wikipedia.org/wiki/Film3',
            'https://en.wikipedia.org/wiki/Film4'
        }

        # Validate the result matches the expected output
        self.assertEqual(result, expected_links)
        print("Test passed: check_title_exists_and_extract_table")

    @patch('requests.get')
    def test_check_title_exists_no_title_column(self, mock_get):
        # Test when the "Title" column does not exist
        scraper = MovieLinkScraper()
        mock_response = Mock()

        # HTML with no "Title" column in the table headers
        mock_response.content = b'''
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
        mock_response.status_code = 200
        mock_get.return_value = mock_response

        # Provide the URL and keyword for the test
        url = "https://en.wikipedia.org/wiki/Test_Page_No_Title"
        wikibase = "https://en.wikipedia.org"
        keyword = "Title"

        # Call the function to test
        result = scraper.check_title_exists_and_extract_table(url, wikibase, keyword)

        # Validate that no links are found since the "Title" column doesn't exist
        self.assertEqual(result, set())
        print("Test passed: No title column found")
    

    @patch('requests.get')
    def test_run(self, mock_get):
        # Create an instance of the MovieLinkScraper
        scraper = MovieLinkScraper()
        
        # Mock responses for both URLs
        # Mock response for the first URL (highest-grossing films)
        mock_response_1 = Mock()
        mock_response_1.content = b'''
        <html>
        <body>
            <table>
                <tr><th>Title</th></tr>
                <tr><td><a href="/wiki/Movie_1">Movie 1</a></td></tr>
                <tr><td><a href="/wiki/Movie_2">Movie 2</a></td></tr>
            </table>
        </body>
        </html>
        '''
        mock_response_1.status_code = 200

        # Mock response for the second URL (worst films)
        mock_response_2 = Mock()
        mock_response_2.content = b'''
        <html>
        <body>
            <table>
                <tr><th>Title</th></tr>
                <tr><td><a href="/wiki/Movie_3">Movie 3</a></td></tr>
                <tr><td><a href="/wiki/Movie_4">Movie 4</a></td></tr>
            </table>
        </body>
        </html>
        '''
        mock_response_2.status_code = 200

        # Set the side effects for the mock get method
        mock_get.side_effect = [mock_response_1, mock_response_2]

        # Define the URLs to scrape
        urls = [
            "https://en.wikipedia.org/wiki/List_of_highest-grossing_films",
            "https://en.wikipedia.org/wiki/List_of_films_considered_the_worst"
        ]

        # Call the run method
        scraper.run(urls)

        # Check that the scraper extracted movie links from both pages
        self.assertEqual(len(scraper.movie_links), 4)  # Total of 4 movies should be extracted
        self.assertEqual(scraper.movie_links[0]['title'], 'Movie 1')
        self.assertEqual(scraper.movie_links[1]['title'], 'Movie 2')
        self.assertEqual(scraper.movie_links[2]['title'], 'Movie 3')
        self.assertEqual(scraper.movie_links[3]['title'], 'Movie 4')

        print("Test passed: run method extracted movie links successfully.")


if __name__ == "__main__":
    unittest.main()