package me.fourtween.webmagic.marksix;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class MarksixPageProcessor implements PageProcessor{

	
	private Site site = Site.me().setRetryTimes(3).setTimeOut(30000).setSleepTime(5000).setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
	
	@Override
	public void process(Page page) {
		int i = 0;
		// TODO Auto-generated method stub
		List<Selectable> lists = page.getHtml().xpath("//table[@id='_ctl0_ContentPlaceHolder1_resultsMarkSix_markSixResultTable']").$("tr").nodes();
		for(Selectable item:lists){
			
//			System.out.println("item:"+item.toString());
//			if(i == 0){
//				i++;
//				continue;
//				
//			}
			i++;
			System.out.println("item"+i);
			List<Selectable> tds = item.$("img").nodes();
			for(Selectable td:tds){
				System.out.println("td:"+td.toString());
			}
			
//			List<Selectable> td1s = item.$("tr").$("table").$("tr").nodes();
//			
//			for(Selectable td:td1s){
//				System.out.println("td1111:"+td.toString());
//			}
			
//			.$("tbody").$("tr")
			
//			System.out.println(item.$("td"));
		}
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
