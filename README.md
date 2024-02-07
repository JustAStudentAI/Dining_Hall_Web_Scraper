# Dining Hall Web Scraper

This project is a Java application designed to scrape and display menu options from the University of Maryland (UMD) dining halls, specifically South Campus, The Y, and 251 North. It leverages Jsoup to parse HTML content, enabling users to select dining halls and meal types dynamically based on the current date.

## Features

- **Dynamic fetching** of daily menu options from UMD dining halls.
- **User selection** for different dining halls and specific meals (Breakfast, Lunch, Dinner, or All).

## Prerequisites

Before running this scraper, ensure you have the following installed:
- Java JDK 11 or newer.
- Maven for dependency management.

## Installation

1. **Clone the Repository**

    Clone the project to your local machine and navigate into the directory:

    ```bash
    git clone https://github.com/JustAStudentAI/Dining_Hall_Web_Scraper.git
    cd Dining_Hall_Web_Scraper
    ```

2. **Install Dependencies**

    Use Maven to install the required dependencies:

    ```bash
    mvn install
    ```

3. **Configuration**

    No additional setup is required to run the scraper, as it accesses public information from the UMD dining services website.

## Usage

To run the scraper, execute the following command in your terminal:

```bash
java -cp target/umd-dining-halls-scraper-1.0-SNAPSHOT.jar webscrape.WebScrape
```

Follow the prompts to select a dining hall and the meal for which you wish to see the menu options. The scraper will then display the relevant menu items.

## Libraries Used

- **Jsoup**: Used for HTML document parsing and extraction.

## License

This project is released under the MIT License. See the LICENSE file for more details.

## Disclaimer

This project is not officially affiliated with, nor endorsed by, the University of Maryland or its Dining Services. It is intended for educational and informational purposes only.
