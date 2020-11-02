import time
import sys
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options
from selenium.common.exceptions import WebDriverException as WDE

args = sys.argv

chrome_options = Options()
chrome_options.add_argument('--headless')
chrome_options.add_argument('--no-sandbox')
chrome_options.add_argument('--disable-dev-shm-usage')

driver = webdriver.Chrome(options=chrome_options)
driver.set_window_size('1200', '1000')
driver.get(args[1])

search = driver.find_element_by_name('query')
search.send_keys('nishipy')
time.sleep(5)

content = driver.find_element_by_class_name('md-search-result__link')
content.click()
time.sleep(5)

driver.quit()