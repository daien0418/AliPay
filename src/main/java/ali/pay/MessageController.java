package ali.pay;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {

//	 private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

	 @RequestMapping(value="accept",method={RequestMethod.GET,RequestMethod.POST})
	 public String accept(HttpServletRequest request,String out_trade_no,String trade_status){
//		 LOG.info(out_trade_no);
//		 LOG.info(trade_status);
		 System.out.println(out_trade_no);
		 System.out.println(trade_status);
		 return "success";
	 }

}
