package Google;



import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;



public class tester {



	public static void main(String[] args) throws Exception {
		String res = HTTPPlaces.post("-33.8670522","151.1957362",500,"restaurant","AIzaSyCbb9-AtcNbXpRLRWaR-fuqDha0SEoss6k");
		System.out.println(res);

	}

}
