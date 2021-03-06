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

public class PersistPipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("get page: " + resultItems.getRequest().getUrl());
		 if(resultItems.getRequest().getUrl().contains("Results.aspx")){
			 try {
			 conn = DBUtils.getConnection();
			 pstmt = conn.prepareStatement("insert ignore into marksixitem (id,serial1,opendate,num1,num2,num3,num4,num5,num6,num7,createtime) "
						+ "values(?,?,?,?,?,?,?,?,?,?,now())");
			 for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			        System.out.println(entry.getKey() + ":\t" + entry.getValue());
			        
			        MarkSixItem item = (MarkSixItem)entry.getValue();
			    	
		        	pstmt.setString(1,IdGen.uuid());
					pstmt.setString(2,item.getId());
					pstmt.setString(3,item.getDate());
					pstmt.setString(4, item.getNum1());
					pstmt.setString(5, item.getNum2());
					pstmt.setString(6, item.getNum3());
					pstmt.setString(7, item.getNum4());
					pstmt.setString(8, item.getNum5());
					pstmt.setString(9, item.getNum6());
					pstmt.setString(10, item.getNum7());
					
					pstmt.execute();
					
			        
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
		 }else{
			 		 
			 try {
				 conn = DBUtils.getConnection();
				 pstmt = conn.prepareStatement("update marksixitem set totalAmount=?,amount1=?,amount2=? ,amount3=? ,amount4=? ,amount5=? ,amount6=? ,amount7=?"
				 		+ ",count1=?,count2=?,count3=?,count4=?,count5=?,count6=?,count7=? where serial1=?");
				 for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				        System.out.println(entry.getKey() + ":\t" + entry.getValue());
				        
				        MarkSixItem item = (MarkSixItem)entry.getValue();
				    	
			        	pstmt.setString(1,item.getTotalAmount());
						pstmt.setString(2,item.getAmount1());
						pstmt.setString(3,item.getAmount2());
						pstmt.setString(4,item.getAmount3());
						pstmt.setString(5,item.getAmount4());
						pstmt.setString(6,item.getAmount5());
						pstmt.setString(7,item.getAmount6());
						pstmt.setString(8,item.getAmount7());
						
						pstmt.setString(9, item.getCount1());
						pstmt.setString(10, item.getCount2());
						pstmt.setString(11, item.getCount3());
						pstmt.setString(12, item.getCount4());
						pstmt.setString(13, item.getCount5());
						pstmt.setString(14, item.getCount6());
						pstmt.setString(15, item.getCount7());
						pstmt.setString(16, item.getId());
						pstmt.execute();
						
				        
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

}
