package me.fourtween.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor{
	

	String author;
	String name;

	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	public void process(Page page) {
		// TODO Auto-generated method stub
		
		if(page.getResultItems().get("author")==null&&author==null) {
			
			page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
			
			author = page.getResultItems().get("author");
			
		}
		
		if(page.getResultItems().get("name")==null&&name==null) {
			page.putField("name", page.getHtml().xpath("//span[@class='p-name vcard-fullname d-block']/text()").toString());
			name = page.getResultItems().get("name");
		}
		
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        System.out.println(page.getHtml().xpath("//span[class='p-name vcard-fullname d-block']/text()").toString());

//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//        	System.out.println("name is null");
//            page.setSkip(true);
//        }
        
//        System.out.println("name is null");
		System.out.println("author:"+author);
        System.out.println("name:"+name);
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
	}

}
