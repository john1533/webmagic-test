package me.fourtween.webmagic.marksix;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.util.TextUtils;
import org.assertj.core.util.Collections;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class KJPageProcessor implements PageProcessor{

	
	private Site site = Site.me().setRetryTimes(3).setTimeOut(30000).setSleepTime(5000).setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
	
	@Override
	public void process(Page page) {
		
		System.out.println("page url:"+page.getUrl());
		
		int i = 0;
		// TODO Auto-generated method stub
//		System.out.println("page:"+page.getHtml());
		String month = null;
		Pattern p = Pattern.compile("https://kjrq.org/d/\\?d=(\\d{4}-\\d{2})");
		Matcher m = p.matcher(page.getUrl().toString());
		if(m.find()){
			month = m.group(1);
			System.out.println("month :"+month);
		}
		
		
			
		if(!TextUtils.isEmpty(month)){
			if(Collections.isNullOrEmpty((List<String>)page.getResultItems().get(month+"kj"))){
				List<String> rets = page.getHtml().xpath("//div[@class='w310 box pm4']//td[@class='td_kj']/text()").all();
				if(!Collections.isNullOrEmpty(rets))
					page.getResultItems().put(month+"kj", rets);
			}
			
			if(Collections.isNullOrEmpty((List<String>)page.getResultItems().get(month+"jdbs2"))){
				List<String> rets = page.getHtml().xpath("//div[@class='w310 box pm4']//td[@class='jdbs2']/text()").all();
				if(!Collections.isNullOrEmpty(rets))
					page.getResultItems().put(month+"jdbs2", rets);
			}
			
			if(Collections.isNullOrEmpty((List<String>)page.getResultItems().get(month+"jdbkj"))){
				List<String> rets = page.getHtml().xpath("//div[@class='w310 box pm4']//td[@class='jdbkj']/text()").all();
				if(!Collections.isNullOrEmpty(rets))
					page.getResultItems().put(month+"jdbkj", rets);
			}
			
		}
		List<String> urls = new ArrayList<String>();
		urls.add("https://kjrq.org/d/?d=2017-08");
		urls.add("https://kjrq.org/d/?d=2017-09");
		urls.add("https://kjrq.org/d/?d=2017-10");
		urls.add("https://kjrq.org/d/?d=2017-11");
		urls.add("https://kjrq.org/d/?d=2017-12");
		urls.add("https://kjrq.org/d/?d=2018-01");
		urls.add("https://kjrq.org/d/?d=2018-02");
		urls.add("https://kjrq.org/d/?d=2018-03");
		urls.add("https://kjrq.org/d/?d=2018-04");
		urls.add("https://kjrq.org/d/?d=2018-05");
		urls.add("https://kjrq.org/d/?d=2018-06");
		
		page.addTargetRequests(urls);
		
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	
	
	private List<String> addPreUrl(List<String> orgs){
		if(CollectionUtils.isEmpty(orgs)){
			return orgs;
		}
		List<String> ret = new ArrayList<String>();
		for(String item:orgs){
			ret.add("http://bet.hkjc.com/marksix/"+item);
		}
		return ret;
	}

}
