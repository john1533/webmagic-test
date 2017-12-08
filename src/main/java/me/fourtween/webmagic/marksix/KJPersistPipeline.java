package me.fourtween.webmagic.marksix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import me.fourtween.webmagic.DBUtils;
import me.fourtween.webmagic.IdGen;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class KJPersistPipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("get page: " + resultItems.getRequest().getUrl());
		
		try {
			 conn = DBUtils.getConnection();
			 pstmt = conn.prepareStatement("insert ignore into kjtime (id,time,type,createtime) "
						+ "values(?,?,?,now())");
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			        String key = entry.getKey();
			        String month = key.substring(0, 7);
			        String type = key.substring(7);
			        System.out.println("month:"+month + ":\t"+"type:"+type + ":\t" + entry.getValue());
			        List<String> values = (List<String>)entry.getValue();
			        for(String item:values){
			        	System.out.println(item);
			        	pstmt.setString(1,IdGen.uuid());
			        	pstmt.setDate(2, new java.sql.Date(sdf.parse(month+"-"+(item.length()==1?"0"+item:item)).getTime()));
			        	pstmt.setString(3, type);
			        	pstmt.execute();
			        	
			        	
			        }
			        
				}
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.closeStatement(pstmt);
			DBUtils.closeConnection(conn);
		}
        
		
	}

}
