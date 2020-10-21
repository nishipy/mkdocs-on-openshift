from selenium import webdriver
import time
from selenium.webdriver.common.keys import Keys
driver = webdriver.Chrome("/usr/local/bin/chromedriver")
driver.get("http://localhost:8000/")

search = driver.find_element_by_name('query')
search.send_keys('nishipy')
time.sleep(5)

content = driver.find_element_by_class_name('md-search-result__link')
content.click()
time.sleep(5)
driver.quit()