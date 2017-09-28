package me.fourtween.webmagic;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
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
