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

public class DailyPageProcessor implements PageProcessor{

	
	private Site site = Site.me().setRetryTimes(3).setTimeOut(30000).setSleepTime(5000).setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
	
	@Override
	public void process(Page page) {
		
		System.out.println("page url:"+page.getUrl());
		
		
		Pattern p = Pattern.compile(".*下期攪珠期數</td>\\s+<td class=\"content\">([/0-9]+)</td>"
				+".*攪珠日期</td>\\s+<td class=\"content\">(\\d{2}/\\d{2}/\\d{4})"
				+".*投注額</td>\\s+<td class=\"content\">(\\$[\\,0-9]+|\\-)"
				+".*多寶 / 金多寶</td>\\s+<td class=\"snowball1\" style=\"line-height:15px;\">(\\$[\\,0-9]+|\\-)"
				+".*估計頭獎基金</td>\\s+<td class=\"snowball1\" style=\"line-height:15px;\">(\\$[\\,0-9]+|\\-)"
			
				+".*valign=\"top\">攪珠期數 : ([/0-9]+)"
				+".*攪珠日期 : (\\d{2}/\\d{2}/\\d{4})"
				+ ".*總投注額 : \\$([0-9\\,]+)</td>"
				+ ".*頭 獎</td>\\s+<td class=\"orangeNum[12]\" align=\"right\">(\\$[\\,0-9]+|\\-)</td>"
				+"\\s+<td class=\"content\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
				+ ".*二 獎</td>\\s+<td class=\"orangeNum[12]\" align=\"right\">(\\$[\\,0-9]+|\\-)</td>"
				+"\\s+<td class=\"content\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
				+ ".*三 獎</td>\\s+<td class=\"orangeNum[12]\" align=\"right\">(\\$[\\,0-9]+|\\-)</td>"
				+"\\s+<td class=\"content\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
				
				+ ".*",Pattern.DOTALL);//.*<td class=\"tableContent2\">頭 獎</td>\\r\\n<td class=\"tableContent2\" align=\"right\"><span class=\"orangeNum2\">$([\\,0-9]+)</span>
		Matcher m = p.matcher(page.getHtml().toString());
		if(m.matches()){
			
			String serial = m.group(6);//当前期数
			Daily dl = page.getResultItems().get(serial);
			if(dl == null){
				dl = new Daily();
				page.getResultItems().put(serial,dl);
				dl.setId(serial);
			}
			
			dl.setNextSerial(m.group(1));
			dl.setNextDate(m.group(2));
			dl.setTz(m.group(3));
			dl.setExt(m.group(4));
			dl.setExpect(m.group(5));
			
			dl.setDate(m.group(7));
			dl.setTotalAmount(m.group(8));
			dl.setAmount1(m.group(9));
			dl.setCount1(m.group(10));
			dl.setAmount2(m.group(11));
			dl.setCount2(m.group(12));
			dl.setAmount3(m.group(13));
			dl.setCount3(m.group(14));
			
			System.out.println("下期攪珠期數 :"+m.group(1));
			System.out.println("下期攪珠日期 :"+m.group(2));
			System.out.println("投注額:"+m.group(3));
			System.out.println("多寶 / 金多寶 :"+m.group(4));
			System.out.println("估計頭獎基金:"+m.group(5));
//			
			
			System.out.println("攪珠期數 :"+m.group(6));
			System.out.println("攪珠日期 :"+m.group(7));
			System.out.println("總投注額 :"+m.group(8));
			System.out.println("頭 獎 :"+m.group(9));
			System.out.println("頭 獎注数 :"+m.group(10));
			System.out.println("二 獎 :"+m.group(11));
			System.out.println("二 獎注数 :"+m.group(12));
			System.out.println("三 獎 :"+m.group(13));
			System.out.println("三 獎注数 :"+m.group(14));
			
//			System.out.println("下期攪珠期數 :"+m.group(10));
//			System.out.println("下期攪珠日期 :"+m.group(11));
//			System.out.println("投注額:"+m.group(12));
//			System.out.println("多寶 / 金多寶 :"+m.group(13));
//			System.out.println("估計頭獎基金:"+m.group(14));
//			
			
			
			List<String> ret = page.getHtml().$("img","src").regex(".*icon/no_(\\d{2}).*").all();
			int num = 1;
			for(String item:ret ){
				switch(num){
					case 1: dl.setNum1(item);
					case 2: dl.setNum2(item);
					case 3: dl.setNum3(item);
					case 4: dl.setNum4(item);
					case 5: dl.setNum5(item);
					case 6: dl.setNum6(item);
					case 7: dl.setNum7(item);
				}
				num++;
				System.out.println("item:"+item);
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
