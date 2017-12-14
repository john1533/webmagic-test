package me.fourtween.webmagic.marksix;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;

import me.fourtween.webmagic.DBUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class DailyScraper {
	
	private static Logger logger = Logger.getLogger(DailyScraper.class);    
	
	private static String lastDay;
	private static String lastDetailDay;
	private static String today;
	private static String nextDay;
	private static String nextDetailDay;
	
	private static int limit;
	private static int detailLimit;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	static HttpClientDownloader httpClientDownloader;
	
	static TimerTask checkTask = new CheckTask();
	
	
	
	public static void main(String[] args){
		Timer timer = new Timer();
		timer.schedule(checkTask, 5*1000, 12*60*60*1000);//每12小时执行一次
		
		timer.schedule(new ScraperTask(), 15*1000, 1*60*1000);//每12小时执行一次
		
		httpClientDownloader = new HttpClientDownloader();
	    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
	    new Proxy("192.168.0.65",8888)
	    ,new Proxy("127.0.0.1",8888)));

	}
	
	private static void catchDetail(){
		
		
			try {
				Spider.create(new DetailPageProcessor())
//	      .addUrl("http://bet.hkjc.com/marksix/Results_Detail.aspx?lang=ch&date=09/12/2017")
				  .addUrl("http://bet.hkjc.com/marksix/Results_Detail.aspx?lang=ch&date="+new SimpleDateFormat("dd/MM/yyyy").format(sdf.parse(nextDetailDay)))
				  .thread(1)
				  .addPipeline(new DetailPersistPipeline().setCallBack(new DetailPersistPipeline.CallBack() {
					@Override
					public void callback(int optRet) {
						// TODO Auto-generated method stub
						if(optRet > 0){
							checkTask.run();
							detailLimit = 10;
						}
					}
				})).setDownloader(httpClientDownloader)
				  .run();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			detailLimit--;
	}
	
	private static void catchDaily(){
		Spider.create(new DailyPageProcessor())
        .addUrl("http://bet.hkjc.com/marksix/index.aspx?lang=ch")
        .thread(1)
        .addPipeline(new DailyPersistPipeline().setCallBack(new DailyPersistPipeline.CallBack() {
			
			@Override
			public void callback(int optRet) {
				// TODO Auto-generated method stub
				if(optRet > 0){
					checkTask.run();
					limit = 10;
				}
				
			}
		})).setDownloader(httpClientDownloader)
        .run();
		limit --;
	}
	
	
	static class CheckTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			today = sdf.format(new Date());
			Connection conn = null;
			Statement stmt = null;
			ResultSet ret = null;
			 try {
				 conn = DBUtils.getConnection();
				 stmt = conn.createStatement();
				 
				 ret = stmt.executeQuery("select max(str_to_date(opendate, '%d/%m/%Y')) from daily");
				 System.out.println("ret:"+ret);
				 if(ret!=null&&ret.next()){
					 System.out.println("ret:"+ret);
					 if(ret.getDate(1)!=null){
						 lastDay = sdf.format(new Date(ret.getDate(1).getTime())); 
					 }
				 }
				 DBUtils.closeResultSet(ret);
				 
				 
				 
				 ret = stmt.executeQuery("select max(str_to_date(opendate, '%d/%m/%Y')) from marksixitem");
				 if(ret!=null&&ret.next()){
					 lastDetailDay = sdf.format(new Date(ret.getDate(1).getTime()));
				 }
				 DBUtils.closeResultSet(ret);
				 
				 
				 
				 String sql = "select max(time) from kjtime where time<='"+today+"'";
				 ret = stmt.executeQuery(sql); 
				 if(ret!=null&&ret.next()){
					 nextDay = sdf.format(new Date(ret.getDate(1).getTime()));
				 }
				 DBUtils.closeResultSet(ret);
				 
				 sql = "select min(time) from kjtime";
				 if(!TextUtils.isEmpty(lastDetailDay)){
					 sql = "select min(time) from kjtime where time>'"+lastDetailDay+"'";
				 }
				 ret = stmt.executeQuery(sql); 
				 if(ret!=null&&ret.next()){
					 nextDetailDay = sdf.format(new Date(ret.getDate(1).getTime()));
				 }
				 DBUtils.closeResultSet(ret);
				 
				 
				 limit = 10;
				 detailLimit = 10;
				 logger.debug("lastDay:"+lastDay+"--lastDetailDay:"+lastDetailDay+"--today:"+today+"--nextDay:"+nextDay+"--nextDetailDay:"+nextDetailDay);
				 
			 }catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
				DBUtils.closeConnection(conn);
				DBUtils.closeStatement(stmt);
			}
		}
		
	}
	
	
	static class ScraperTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			logger.debug("ScraperTask lastDay:"+lastDay+"--lastDetailDay:"+lastDetailDay+"--today:"+today+"--nextDay:"+nextDay+"--nextDetailDay:"+nextDetailDay+"--limit:"+limit+"--detailLimit:"+detailLimit);
			Calendar c = Calendar.getInstance();
			
			int curH = c.get(Calendar.HOUR_OF_DAY);
			int curM = c.get(Calendar.MINUTE);
			
			
			if((lastDay==null||nextDay==null||(nextDay.compareTo(lastDay)>0&&nextDay.compareTo(today)<0))&&limit>0){
				catchDaily();
			}else if(today.equals(nextDay)){//开奖日
				if((curH>21||(curH==21&&curM>=30))&&limit>0){
					logger.debug("开奖日");
					catchDaily();
				}
			}
			
			if((lastDetailDay==null||nextDetailDay==null||(nextDetailDay.compareTo(lastDetailDay)>0&&nextDetailDay.compareTo(today)<0))&&detailLimit>0){
				catchDetail();
			}else if(today.equals(nextDetailDay)){//开奖日
				if((curH>21||(curH==21&&curM>=30))&&detailLimit>0){
						logger.debug("开奖日");
						catchDetail();
				}
			}
			
		}
		
	}
}
