import time
import requests
from colorama import Fore
from lxml.html import fromstring
from bs4 import BeautifulSoup
import json

prev = time.time()

class MovieLinkScraper:
    def __init__(self):
        self._selector = None
        self.movie_links = []

    def _extract_movie_links(self):
        """Extract all movie links from a Wikipedia list/category page."""
        # This XPath targets all links in the content section of the page
        links = self._selector.xpath('//div[@id="mw-content-text"]//a[starts-with(@href, "/wiki/") and not(contains(@href, ":"))]')
        
        for link in links:
            href = link.xpath(".//@href")[0]
            movie_title = ''.join(link.xpath(".//text()")).strip()
            full_url = f"https://en.wikipedia.org{href}"
            # self.check_title_exists_and_extract_table(full_url)
            # Save the movie title and URL in a list
            self.movie_links.append({"title": movie_title, "url": full_url})

    def check_title_exists_and_extract_table(self, url, wikibase, keyword):
        try:
            # Fetch the page content
            response = requests.get(url)
            response.raise_for_status()  # Raise an error for bad responses

            # Parse the HTML content
            html_content = response.content

            # Check if the <th> element with title "Title" exists
            #title_header = html_content.xpath('//th[normalize-space(text())="Title"]')
            soup = BeautifulSoup(html_content, 'html.parser')
            tables = soup.find_all('table')
            print(f"Found {len(tables)} tables.")
            links = []

            for table in tables:
                th_list = table.find_all('th')
                title_column_index = None
                
                 # Check if any <th> contains the text "Title" and find its index
                for idx, th in enumerate(th_list):
                    if keyword.lower() in th.get_text().lower():
                        title_column_index = idx
                        break  # We found the "Title" column, no need to check furthers

                # If the table has a "Title" column, print the column data
                if title_column_index is not None:
                    rows = table.find_all('tr')  # Find all rows in the table

                    # Iterate through each row and extract data from the "Title" column
                    for row in rows:
                        splitted_row = row.find_all(['th', 'td'])
                        if len(splitted_row) > title_column_index:
                            splitted_row = splitted_row[title_column_index]
                        else:
                            splitted_row = splitted_row[0]
                        cells = splitted_row.find_all(['a'])
                        for cell in cells:
                            #print(cell)
                            link = cell.get('href')
                            links.append(link)

            set_link = set(links)
            filtered_links = [link for link in set_link if link != None and '/' in link and '_in_film' not in link]
            result_list = set(map(lambda x: wikibase + x, filtered_links))
            return result_list
        
                
                #for th in th_list:
                #    link = th.find_all('a')
                #    #print(th)
                #    if link:
                #        full_url = f'https://en.wikipedia.org{link['href']}'
                #        print(full_url)


                #if table:
                #    links = []
#
                #    # Print all rows and their columns
                #    for row in table.find_all('tr')[1:]:
                #        title_cell = row.find('td')  # Extract link from title from <td> elements
#
                #        if title_cell:
                #            link = title_cell.find('a')
#
                #            if link and 'href' in link.attrs:
                #                full_url = f'https://en.wikipedia.org{link}'
                #                print(full_url)
                #                links.append(full_url)
                #else:
                #print("The 'Title' header does not exist.")
                #    return False
        except requests.exceptions.RequestException as e:
            return False

    def fetch_and_extract(self, url):
        """Fetch the Wikipedia page and extract movie links."""
        try:
            response = requests.get(url)
            response.raise_for_status()  # Raise an error if the request fails
            self._selector = fromstring(response.content)
            self._extract_movie_links()
        except requests.RequestException as e:
            print(Fore.RED + f"Error fetching URL: {e}" + Fore.RESET)
            return

    def run(self, urls):
        """Run the scraper for a list of Wikipedia URLs."""
        for url in urls:
            print(Fore.YELLOW + f"Fetching {url}..." + Fore.RESET)
            self.fetch_and_extract(url)
            print(Fore.GREEN + f"Extracted {len(self.movie_links)} movie links from {url}" + Fore.RESET)

if __name__ == "__main__":
    # Example list page from Wikipedia containing a list of films
    # https://de.wikipedia.org/wiki/IMDb_Top_250_Movies
    # https://en.wikipedia.org/wiki/List_of_highest-grossing_films
    url = "https://en.wikipedia.org/wiki/List_of_highest-grossing_films"  # Replace with relevant Wikipedia pages
    wikipedia_str = 'https://en.wikipedia.org'
    keyword = "title"
    result_list = []
    
    scraper = MovieLinkScraper()
    result_list = scraper.check_title_exists_and_extract_table(url, wikipedia_str, keyword)
    for link in result_list:
        print(link)
    with open("movie_link.json", "w") as json_file:
        json.dump(list(result_list), json_file)
    #scraper.run(urls)

    # Print out all extracted movie links
    #for movie in scraper.movie_links:
            # scraper.check_title_exists_and_extract_table(movie['url'])
            # print(f"Title: {movie['title']}, URL: {movie['url']}")s