package me.fourtween.webmagic.marksix;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class MsWebScraper {

	 private static Logger logger = Logger.getLogger(MsWebScraper.class);    
	public static void main(String[] args) {
		BasicConfigurator.configure();
		logger.debug("MsWebScraper engine starting");
		
		
		
		Request request = new Request();
		request.setUrl("http://bet.hkjc.com/marksix/Results.aspx?lang=ch&nextpage=2&initpage=1")
		.setMethod("POST")
//		.addHeader("Host","bet.hkjc.com").addHeader("Proxy-Connection", "keep-alive")
//		.addHeader("Origin","http://bet.hkjc.com").addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
		.setRequestBody(createRequestBody());
		
//		Request request = new Request("http://bet.hkjc.com/marksix/Results.aspx?lang=ch&nextpage=2&initpage=1");
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("__VIEWSTATE","/wEPDwUKMTE4NTM4MDg3MGRk5p/mNmsOnVCJh3AYgUhW4wHxc5A=");
//		map.put("txtRandomNum","");
//		map.put("selectDrawID","10");
//		map.put("hiddenSelectDrawID","10");
//		map.put("radioDrawRange","");
//		map.put("GetDrawDate&_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawFromMonth", "01");
//		map.put("hiddenSelectDrawFromMonth", "11");
//		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawFromYear", "2017");
//		map.put("hiddenSelectDrawFromYear", "2017");
//		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawToMonth", "12");
//		map.put("hiddenSelectDrawToMonth", "12");
//		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawToYear", "2017");
//		map.put("hiddenSelectDrawToYear", "2017");
//		map.put("radioResultType", "GetAll");
//		request.setMethod("POST").setExtras(map);
		
		
		
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
	    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
	    new Proxy("192.168.0.65",8888)
	    ,new Proxy("127.0.0.1",8888)));
		
		
		Spider.create(new MarksixPageProcessor())
        .addRequest(request)
        .thread(1)
        .addPipeline(new ConsolePipeline()).setDownloader(httpClientDownloader)
        .run();
	}
	
	public static HttpRequestBody createRequestBody(){
//		HttpRequestBody requestBody = new HttpRequestBody();
//		requestBody.setContentType(HttpRequestBody.ContentType.FORM);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("__VIEWSTATE","/wEPDwUKMTE4NTM4MDg3MGRk5p/mNmsOnVCJh3AYgUhW4wHxc5A=");
		map.put("txtRandomNum","");
		map.put("selectDrawID","10");
		map.put("hiddenSelectDrawID","10");
		map.put("radioDrawRange","");
		map.put("GetDrawDate&_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawFromMonth", "01");
		map.put("hiddenSelectDrawFromMonth", "11");
		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawFromYear", "2017");
		map.put("hiddenSelectDrawFromYear", "2017");
		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawToMonth", "12");
		map.put("hiddenSelectDrawToMonth", "12");
		map.put("_ctl0:ContentPlaceHolder1:resultsMarkSix:selectDrawToYear", "2017");
		map.put("hiddenSelectDrawToYear", "2017");
		map.put("radioResultType", "GetAll");
		return HttpRequestBody.form(map, "utf8");
//		return requestBody;
	}

}
