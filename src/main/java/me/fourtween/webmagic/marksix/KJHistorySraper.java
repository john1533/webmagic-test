package me.fourtween.webmagic.marksix;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class KJHistorySraper {
	private static Logger logger = Logger.getLogger(KJHistorySraper.class);    
	public static void main(String[] args){
		for(int year=2010;year<=2017;year++){
			for(int month = 1;month<=12;month++){
				String strM = ""+month;
				if(month<10){
					strM = "0"+month;
				}
				logger.debug("MsWebScraper engine starting");
				HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
			    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
			    new Proxy("192.168.0.65",8888)
			    ,new Proxy("127.0.0.1",8888)));
				Spider.create(new MarksixPageProcessor())
		        .addRequest(createRquest(""+year,strM))
		        .thread(1)
		        .addPipeline(new PersistPipeline()).setDownloader(httpClientDownloader)
		        .run();
			}
		}
	}
	
	public static HttpRequestBody createRequestBody(String year,String month){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("__VIEWSTATE","/wEPDwUKMTE4NTM4MDg3MGRk5p/mNmsOnVCJh3AYgUhW4wHxc5A=");
		map.put("txtRandomNum","");
		map.put("selectDrawID",month);
		map.put("hiddenSelectDrawID",month);
		map.put("radioDrawRange","GetDrawDate");
		map.put("GetDrawDate&_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawFromMonth", month);
		map.put("hiddenSelectDrawFromMonth", month);
		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawFromYear", year);
		map.put("hiddenSelectDrawFromYear", year);
		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawToMonth", month);
		map.put("hiddenSelectDrawToMonth", month);
		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawToYear", year);
		map.put("hiddenSelectDrawToYear", year);
		map.put("radioResultType", "GetAll");
		return HttpRequestBody.form(map, "utf8");
	}
	
	public static Request createRquest(String year,String month){
		Request request = new Request();
		request.setUrl("http://bet.hkjc.com/marksix/Results.aspx?lang=ch") 
		.setMethod("POST")
		.setRequestBody(createRequestBody(year,month));
		return request;
	}
}
