package me.fourtween.webmagic.marksix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import me.fourtween.webmagic.DBUtils;
import me.fourtween.webmagic.IdGen;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class DailyPersistPipeline implements Pipeline{

	CallBack callBack;
	public static interface CallBack{
		public void callback(int optRet);
	}
	
	public CallBack getCallBack() {
		return callBack;
	}

	public DailyPersistPipeline setCallBack(CallBack callBack) {
		this.callBack = callBack;
		return this;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("get page: " + resultItems.getRequest().getUrl());
		 try {
		 conn = DBUtils.getConnection();
		 pstmt = conn.prepareStatement("insert ignore into daily (id,serial1,opendate,num1,num2,num3,num4,num5,num6,num7,totalAmount,amount1,amount2,amount3"
			 		+ ",count1,count2,count3,nextserial,nextopen,jdb,expect,createtime) "
				 
			 		
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())");
		 for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
		        System.out.println(entry.getKey() + ":\t" + entry.getValue());
		        
		        Daily item = (Daily)entry.getValue();
		    	
	        	pstmt.setString(1,IdGen.uuid());
				pstmt.setString(2,item.getId());
				pstmt.setString(3,item.getDate());
				pstmt.setString(4, item.getNum1());
				pstmt.setString(5, item.getNum2());
				pstmt.setString(6, item.getNum3());
				pstmt.setString(7, item.getNum4());
				pstmt.setString(8, item.getNum5());
				pstmt.setString(9, item.getNum6());
				pstmt.setString(10,item.getNum7());
				
				pstmt.setString(11,item.getTotalAmount());
				pstmt.setString(12,item.getAmount1());
				pstmt.setString(13,item.getAmount2());
				pstmt.setString(14, item.getAmount3());
				
				pstmt.setString(15, item.getCount1());
				pstmt.setString(16, item.getCount2());
				pstmt.setString(17, item.getCount3());
				
				pstmt.setString(18, item.getNextSerial());
				pstmt.setString(19, item.getNextDate());
				pstmt.setString(20,item.getExt());
				pstmt.setString(21,item.getExpect());
				
				int ret = pstmt.executeUpdate();
				if(callBack != null){
					callBack.callback(ret);
				}
		        
		    }
		 } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.closeStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
        
	}

}
