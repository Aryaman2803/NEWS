# NewsApp
It uses newsapi.org API to get news and get JSON data back.Then it is parsed using google's gson library and the requests are sent by Retrofit.

**Note: To run the app you have to add [NewsAPI.org](https://newsapi.org/) key. I have taken this step to due to limited access to daily request for News. Getting a key would take just few seconds :)**

**Setup API Key**

1. Visit NewsAPI.org to get your API Key
2. Copy your API Key from accounts section
3. Open gradle.properties (Create the file if not exists)
   `App\java\com.example.news\utility.java`
4. Add the following line:
    `final String API_KEY = "YOUR NEWS API KEY";`


# App Screenshot
![](Images/1.PNG)



![](Images/image2.PNG)

![](Images/3.PNG)

![](Images/image4.PNG)
