package me.fourtween.webmagic.marksix;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.util.TextUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class MarksixPageProcessor implements PageProcessor{

	
	private Site site = Site.me().setRetryTimes(3).setTimeOut(30000).setSleepTime(5000).setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
	
	@Override
	public void process(Page page) {
		
		
		
		System.out.println("page url:"+page.getUrl());
		
		if(page.getUrl().toString().contains("Results.aspx")){
			int i = 0;
			// TODO Auto-generated method stub
			List<Selectable> lists = page.getHtml().xpath("//table[@id='_ctl0_ContentPlaceHolder1_resultsMarkSix_markSixResultTable']//tr").nodes();
			for(Selectable item:lists){
				System.out.println("item"+i);
				String as = item.$("a","text").toString();
				System.out.println("as:"+as);
				if(TextUtils.isEmpty(as)){
					continue;
				}
				i++;
				
				MarkSixItem ms = new MarkSixItem();
				ms.setId(as);
				
				
				
				String ds = item.regex("<td class=\"tableResult[12]\">(\\d{2}/\\d{2}/\\d{4})</td>").toString();
//				for(String d:ds){
					System.out.println("date:"+ds);
//				}
				ms.setDate(ds);
					
				String es = item.regex("<td class=\"tableResult[12]\">([\u4e00-\u9fa5]+)</td>").toString();
				System.out.println("加多宝:"+es);
//				List<String> es = item.regex("<td class=\"tableResult[12]\">([\u4e00-\u9fa5]+)</td>").all();
//				for(String e:es){
//					System.out.println("加多宝:"+e);
//				}
				if(!TextUtils.isEmpty(es)){
					ms.setExtName(es);
				}
				
				
//				List<Selectable> tds = item.$("td").nodes();
//				for(Selectable td:tds){
//					System.out.println("td:"+td.toString());
//				}
				
				
//				String as = item.$("a","text").toString();
//				System.out.println("as:"+as);
//				List<Selectable> as = item.$("a","text").nodes();
//				for(Selectable a:as){
//					System.out.println("a:"+a.toString());
//				}
				
				List<String> als = item.$("a","href").all();
				for(String al:als){
					System.out.println("al:"+al.toString());
				}
				
				int num = 1;
				Pattern p = Pattern.compile(".*(\\d{2})_s.*");
				List<Selectable> imgs = item.$("img","src").nodes();
				for(Selectable img:imgs){
//					/marksix/info/images/icon/no_03_s.gif?CV=L208R2h
					
//					p.matcher(img.toString()).group(1);
					Matcher m = p.matcher(img.toString());
					if(m.find()){
						System.out.println("img:"+img.toString()+"---:"+m.group(1));
//						System.out.println("---:"+m.group(1));
						switch(num){
							case 1: ms.setNum1(m.group(1));
							case 2: ms.setNum2(m.group(1));
							case 3: ms.setNum3(m.group(1));
							case 4: ms.setNum4(m.group(1));
							case 5: ms.setNum5(m.group(1));
							case 6: ms.setNum6(m.group(1));
							case 7: ms.setNum7(m.group(1));
						}
						num++;
					}
				}
//				page.addTargetRequests(addPreUrl(als));
			}
		}
		
		
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
