package com.project.media.guzeyuk;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.lang.String;
import java.io.IOException;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.util.Log;

public class Crawling extends AsyncTask<String, Void , ArrayList>
{
    public static ArrayList<String> farmList;

    @Override
    protected  void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(String...params) {
        try {
            String barcode = params[0];
            farmList = new ArrayList<String>();
            System.out.println(barcode);
            //돼지고기 크롤링
            if (barcode.charAt(0) == 'L' ) {
                String url = "http://pig.mtrace.go.kr/viewbreading/lotNo/lotNoSearch.do?lotNo=" + barcode;
                Document doc = Jsoup.connect(url).get();

                Elements table = doc.select("table[class=def type2]").select("tbody").select("td");

                for (int i = 1; i < table.size(); i++) {
                    if (i % 4 == 2) {
                        farmList.add((table.get(i).text()));
                    }
                }
            }
            //소고기 크롤링
            else {
                String url = "http://cattle.mtrace.go.kr/viewbreading/cattle/cattleSearch.do?btsProgNo=0109008401&btsActionMethod=SELECT&cattleNo=" + barcode;
                Document doc = Jsoup.connect(url).get();

                Elements table = doc.select("table[class=def type2]").select("tbody").select("td");

                for (int i = 1; i < table.size(); i++) {
                    if (i % 4 == 3) {
                        farmList.add((table.get(i).text()));
                    }
                }
            }
            System.out.println(farmList.size());
            System.out.println(farmList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return farmList;
    }

    @Override
    protected void onPostExecute(ArrayList farmList) {
        super.onPostExecute(farmList);
    }

    }



