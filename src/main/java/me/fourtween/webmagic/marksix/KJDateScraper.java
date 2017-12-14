package me.fourtween.webmagic.marksix;

import org.apache.log4j.BasicConfigurator;

import us.codecraft.webmagic.Spider;

public class KJDateScraper {
	public static void main(String[] args){
		//开奖日期
		BasicConfigurator.configure();
		Spider.create(new KJPageProcessor())
        .addUrl("https://kjrq.org/d/?d=2017-07")
        .thread(5)
        .addPipeline(new KJPersistPipeline())
        .run();
	}
}
