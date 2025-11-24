package Assignment.Tekmonk.Service;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ServiceController {

    private static final String url = "https://time.com";


    public List<Map<String, String>> getStory()   {

        List<Map<String,String>> result = new ArrayList<>();

        try {
            String allContent = fetch(url); // all the site content in html form


            String sectionToExtract = "Voices";

            int startIndex = allContent.indexOf(sectionToExtract);


            if (startIndex != -1) {

                String matchContent = allContent.substring(startIndex);


                // the reg patter is used to extract the things which is present inside  the href or the span tag
                Pattern storyPattern = Pattern.compile(
                        "<a[^>]*href=\"([^\"]+)\"[^>]*>\\s*<span[^>]*>(.*?)</span>",
                        Pattern.DOTALL
                );
                Matcher matcher = storyPattern.matcher(matchContent);

                int count = 0;

                while (matcher.find() && count < 6) {
                    String link = matcher.group(1);

                    String title = matcher.group(2);


                    title = title.replaceAll("<[^>]*>", "").trim();

                    Map<String, String> story = new HashMap<>();
                    story.put("title", title);
                    story.put("link", link);
                    result.add(story);
                    count++;

                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        return result;
    }

    private String fetch(String url) throws IOException {

        URL data = new URL(url);



        // it data.openConnection() it return thee URLConnection but
        // i have to sent an http request that's why i cast into the HttpURLConnection
        HttpURLConnection con = (HttpURLConnection) data.openConnection();


        con.setRequestMethod("GET"); // request is not send at this point it is prepared to send


        // here the request is sent when we call the con.getInputStream();

        InputStream Data = con.getInputStream(); // at this point of time the request is send

        // Data is in Raw Form

        String content = new String(Data.readAllBytes());
        con.disconnect();

        return content;
    }
}
