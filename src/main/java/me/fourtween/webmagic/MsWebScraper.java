package me.fourtween.webmagic;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class MsWebScraper {

	 private static Logger logger = Logger.getLogger(MsWebScraper.class);    
	public static void main(String[] args) {
		BasicConfigurator.configure();
		logger.debug("MsWebScraper engine starting");
		// TODO Auto-generated method stub
		Spider.create(new GithubRepoPageProcessor())
        //从"https://github.com/code4craft"开始抓
        .addUrl("https://github.com/code4craft")
        //开启5个线程抓取
        .thread(5)
        .addPipeline(new ConsolePipeline())
        //启动爬虫
        .run();
	}

}
