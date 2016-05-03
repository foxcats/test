/*import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GUI_EX {
	
	public static void main(String[] args){
		JFrame frame=new JFrame("Hello Program");
		frame.setPreferredSize(new Dimension(200,70));
		frame.setLocation(500,400);
		Container contentPane=frame.getContentPane();
		JTextField text=new JTextField();
		JTextArea textArea=new JTextArea();
		JButton button=new JButton("확인");
		JLabel label=new JLabel("Hello");
		contentPane.add(text,BorderLayout.CENTER);
		contentPane.add(button,BorderLayout.EAST);
		contentPane.add(label,BorderLayout.SOUTH);
		ActionListener listener=new ConfirmButtonActionListener(text,label);
		button.addActionListener(listener);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
}
 
class ConfirmButtonActionListener implements ActionListener{
		JTextField text;
		JLabel label;
		ConfirmButtonActionListener(JTextField text,JLabel label){
			this.text=text;
			this.label=label;
			
		}
		
		public void actionPerformed(ActionEvent e){
			String name=text.getText();
			label.setText("Hello "+name);
		}
	}*/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class GUI_EX implements ActionListener {
 JTextArea text; // JTextArea 선언
 int i=1;
 int k=1;
 QueryResult result;
 
 JTextField text2=new JTextField();
 public static void main(String[] args) {
	 GUI_EX boogi = new GUI_EX();
  boogi.go();
 }
 
 public void go() {
  JFrame frame = new JFrame(); // 프레임 생성
  JPanel panel = new JPanel(); // 패널 생성
  JButton button = new JButton("확인"); // 버튼 생성
  button.addActionListener(this);
  // 10행 20열짜리 JTextArea.
  text = new JTextArea(50,50);
  // 행 넘기기 기능(line wrapping)을 켭니다.
  text.setLineWrap(true);
  
  // JScrollPane을 만들고 그 객체에 스크롤 기능을 추가해야 하는 텍스트 영역을 전달.
  JScrollPane scroller = new JScrollPane(text);
  // 스크롤 틀에 수직 방향의 스크롤 바만 집어넣도록 지시합니다.
  scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
  
  // 중요한 부분!! 텍스트 영역을(스크롤 틀 생성자를 통해서) 스크롤 틀에 전달한 다음
  // 그 스크롤 틀을 패널에 추가. 텍스트 영역을 패널에 직접 추가하지 않음.
  panel.add(scroller);
  
  frame.getContentPane().add(BorderLayout.SOUTH, panel);
  frame.getContentPane().add(BorderLayout.EAST, button);
  frame.getContentPane().add(BorderLayout.CENTER, text2);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.pack();
  frame.setVisible(true);
 }
 
 public void actionPerformed(ActionEvent event) {
	 Query query=new Query("따뜻");
	 String str=text2.getText();
	 List<String> arrList=getTweetText(query,str);
	 Iterator<String>iterator =arrList.iterator();
     while(iterator.hasNext()) {
    	 text.append(iterator.next().toString()+"\n"); // text영역에 붙여넣기
    	  text.requestFocus(); // 커서를 text로 돌려놓기.
     }
  
 }
 
 public List<String> getTweetText(Query query,String str){
	 
	 ConfigurationBuilder cb=new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("RcZhWdgKSgak3yZAUQhc9lGRk")
		.setOAuthConsumerSecret("CnFLs0MmcGhBYx6PYTrdUKSX0pxvzubUVVI2h1UktDhDR2vbUh")
		.setOAuthAccessToken("2976195565-6NYTdmRHd7RsqB44lUHQayr6ofGRPpZsTXNAdeU")
		.setOAuthAccessTokenSecret("vuyBoUzRvTJqJIwF4Zx1VktSY3bjBi7YInhftoyOyKxXs");
		
		TwitterFactory tf=new TwitterFactory(cb.build());
		Twitter twitter=tf.getInstance();
		 List<String> arrList = new ArrayList<String>();
     try {
         query.since(str);
         query.count(100);
         do {
        	 
        	 if(i>=10000)
        	 {
        		 try {
        	         Thread.sleep(900000);
        	     } catch (InterruptedException e) {
        	 }
        		 i=1;
        	 }
             result = twitter.search(query);
             
             
            /*try{
             result=twitter.search(result.nextQuery());
             }catch(NullPointerException e)
             {
             	break;
             }*/
             
             List<Status> tweets = result.getTweets();
             
             for (Status tweet : tweets) {
                 arrList.add("@" + tweet.getUser().getScreenName() + " - "
             +tweet.getCreatedAt().toString()+"-"+ tweet.getText()+"  "+i);
                 i++;
                 k++;
                 try {
                     Thread.sleep(10);
                 } catch (InterruptedException e) {
                 
             }
              
             }
         } while ((query = result.nextQuery()) != null);
     } catch (TwitterException te) {
         te.printStackTrace();
         System.out.println("Failed to search tweets: " + te.getMessage());
         System.exit(-1);
     }
     
     return  arrList;
 }
 
}
