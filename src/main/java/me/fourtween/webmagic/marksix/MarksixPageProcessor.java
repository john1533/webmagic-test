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
				
				String ds = item.regex("<td class=\"tableResult[12]\">(\\d{2}/\\d{2}/\\d{4})</td>").toString();
				if(TextUtils.isEmpty(ds)){
					continue;
				}
				i++;
				String as = item.$("a","text").regex("([/0-9]+)").toString();//期数
				
				if(TextUtils.isEmpty(as)){
					as = item.regex("td class=\"tableResult[12]\">(\\d{2}/\\d{3})&nbsp").toString();//td class="tableResult2">94/032&nbsp
				}
				
				System.out.println("item"+i);
				System.out.println("as:"+as);
				
				MarkSixItem ms = page.getResultItems().get(as);
				if(ms == null){
					ms = new MarkSixItem();
					page.getResultItems().put(as, ms);
				}
				ms.setId(as);
				
//				String ds = item.regex("<td class=\"tableResult[12]\">(\\d{2}/\\d{2}/\\d{4})</td>").toString();
//				System.out.println("date:"+ds);
				ms.setDate(ds);
					
				String es = item.regex("<td class=\"tableResult[12]\">([\u4e00-\u9fa5]+)</td>").toString();
//				System.out.println("加多宝:"+es);
				if(!TextUtils.isEmpty(es)){
					ms.setExtName(es);
				}
				
				List<String> als = item.$("a","href").all();
				for(String al:als){
//					System.out.println("al:"+al.toString());
				}
				
				int num = 1;
				Pattern p = Pattern.compile(".*(\\d{2})_s.*");
				List<Selectable> imgs = item.$("img","src").nodes();
				for(Selectable img:imgs){
					Matcher m = p.matcher(img.toString());
					if(m.find()){
						System.out.println("img:"+img.toString()+"---:"+m.group(1));
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
				if(!CollectionUtils.isEmpty(als)){
					page.addTargetRequests(addPreUrl(als));
				}		
			}
		}else{
			
			
//			<table width="100%" border="0" cellspacing="0" cellpadding="2">
//			<tr>
//				<td width="20%" class="tableContentHead"><strong>獎項</strong></td>
//				<td class="tableContentHead" align="right"><strong>每注獎金</strong></td>
//				<td width="35%" align="right" class="tableContentHead"><strong>中獎注數</strong></td>
//			</tr>
//			<tr>
//				<td class="tableContent2">頭 獎</td>
//				<td class="tableContent2" align="right"><span class="orangeNum2">-</span></td>
//				<td class="tableContent2" align="right">-</td>
//			</tr>
//			<tr>
//				<td class="tableContent1">二 獎</td>
//				<td class="tableContent1" align="right"><span class="orangeNum1">$532,990</span></td>
//				<td class="tableContent1" align="right">2.5</td>
//			</tr>
//			<tr>
//				<td class="tableContent2">三 獎</td>
//				<td class="tableContent2" align="right"><span class="orangeNum1">$102,990</span></td>
//				<td class="tableContent2" align="right">34.5</td>
//			</tr>
//			<tr >
//				<td class="tableContent1">&nbsp;</td>
//				<td class="tableContent1" align="right">&nbsp;</td>
//				<td class="tableContent1" align="right">&nbsp;</td>
//			</tr>
//		</table>
//	</td>
//	<td>
//		<table width="100%" border="0" cellspacing="0" cellpadding="2">																			
//			<tr>
//				<td width="20%" class="tableContentHead"><strong>獎項</strong></td>
//				<td class="tableContentHead" align="right"><strong>每注獎金</strong></td>
//				<td width="35%" align="right" class="tableContentHead"><strong>中獎注數</strong></td>
//			</tr>
//			<tr>
//				<td class="tableContent2">四 獎</td>
//				<td class="tableContent2" align="right"><span class="orangeNum1">$9,600</span></td>
//				<td class="tableContent2" align="right">180.5</td>
//			</tr>
//			<tr>
//				<td class="tableContent1">五 獎</td>
//				<td class="tableContent1" align="right"><span class="orangeNum1">$640</span></td>
//				<td class="tableContent1" align="right">3,118.7</td>
//			</tr>
//			<tr>
//				<td class="tableContent2">六 獎</td>
//				<td class="tableContent2" align="right"><span class="orangeNum1">$320</span></td>
//				<td class="tableContent2" align="right">5,562.0</td>
//			</tr>
//			<tr >
//				<td class="tableContent1">七 獎</td>
//				<td class="tableContent1" align="right"><span class="orangeNum1">$40</span></td>
//				<td class="tableContent1" align="right">56,684.4</td>
//			</tr>

			
			
			Pattern p = Pattern.compile(".*攪珠期數 : ([/0-9]+)"
					+ ".*總投注額 : \\$([0-9\\,]+)</td>"
					+ ".*頭 獎.*<span class=\"orangeNum[12]\">(\\$[\\,0-9]+|\\-)</span></td>"
					+"\\s+<td class=\"tableContent[12]\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
					+ ".*二 獎</td>\\s+<td class=\"tableContent[12]\" align=\"right\"><span class=\"orangeNum[12]\">(\\$[\\,0-9]+|\\-)</span></td>"
					+"\\s+<td class=\"tableContent[12]\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
					+ ".*三 獎</td>\\s+<td class=\"tableContent[12]\" align=\"right\"><span class=\"orangeNum[12]\">(\\$[\\,0-9]+|\\-)</span></td>"
					+"\\s+<td class=\"tableContent[12]\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
					+ ".*四 獎</td>\\s+<td class=\"tableContent[12]\" align=\"right\"><span class=\"orangeNum[12]\">(\\$[\\,0-9]+|\\-)</span></td>"
					+"\\s+<td class=\"tableContent[12]\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
					+ ".*五 獎</td>\\s+<td class=\"tableContent[12]\" align=\"right\"><span class=\"orangeNum[12]\">(\\$[\\,0-9]+|\\-)</span></td>"
					+"\\s+<td class=\"tableContent[12]\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
					+ ".*六 獎</td>\\s+<td class=\"tableContent[12]\" align=\"right\"><span class=\"orangeNum[12]\">(\\$[\\,0-9]+|\\-)</span></td>"
					+"\\s+<td class=\"tableContent[12]\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
					+ ".*七 獎</td>\\s+<td class=\"tableContent[12]\" align=\"right\"><span class=\"orangeNum[12]\">(\\$[\\,0-9]+|\\-)</span></td>"
					+"\\s+<td class=\"tableContent[12]\" align=\"right\">([\\.\\,0-9]+|\\-)</td>"
					+ ".*",Pattern.DOTALL);//.*<td class=\"tableContent2\">頭 獎</td>\\r\\n<td class=\"tableContent2\" align=\"right\"><span class=\"orangeNum2\">$([\\,0-9]+)</span>
			Matcher m = p.matcher(page.getHtml().toString());
			if(m.matches()){
				String as = m.group(1);
				MarkSixItem ms = page.getResultItems().get(as);
				if(ms == null){
					ms = new MarkSixItem();
					page.getResultItems().put(as, ms);
					ms.setId(as);
				}
				ms.setTotalAmount(m.group(2));
				System.out.println("攪珠期數 :"+as);
				System.out.println("總投注額 :"+m.group(2));
				System.out.println("頭 獎 :"+m.group(3));
				ms.setAmount1(m.group(3).replace("\\$", ""));
				System.out.println("頭 獎注数 :"+m.group(4));
				ms.setCount1(m.group(4));
				System.out.println("二 獎 :"+m.group(5));
				ms.setAmount2(m.group(5).replace("\\$", ""));
				System.out.println("二 獎注数 :"+m.group(6));
				ms.setCount2(m.group(6));
				System.out.println("三 獎 :"+m.group(7));
				ms.setAmount3(m.group(7).replace("\\$", ""));
				System.out.println("三 獎注数 :"+m.group(8));
				ms.setCount3(m.group(8));
				
				ms.setAmount4(m.group(9).replace("\\$", ""));
				ms.setCount4(m.group(10));
				ms.setAmount5(m.group(11).replace("\\$", ""));
				ms.setCount5(m.group(12));
				ms.setAmount6(m.group(13).replace("\\$", ""));
				ms.setCount6(m.group(14));
				ms.setAmount7(m.group(15).replace("\\$", ""));
				ms.setCount7(m.group(16));
				System.out.println("四 獎 :"+m.group(9));
				System.out.println("四 獎注数 :"+m.group(10));
				System.out.println("五 獎 :"+m.group(11));
				System.out.println("五 獎注数 :"+m.group(12));
				System.out.println("六 獎 :"+m.group(13));
				System.out.println("六 獎注数 :"+m.group(14));
				System.out.println("七 獎 :"+m.group(15));
				System.out.println("七 獎注数 :"+m.group(16));
			}
//			m.matches();
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
