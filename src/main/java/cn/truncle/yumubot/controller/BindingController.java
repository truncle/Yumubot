//package cn.truncle.yumubot.controller;
//
//
//import cn.truncle.yumubot.model.BinUser;
//import cn.truncle.yumubot.service.CQService;
//import cn.truncle.yumubot.service.OSUV2Service;
//import cn.truncle.yumubot.util.BindingUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(produces = "application/json;charset=UTF-8")
//public class BindingController {
//    @Autowired
//    OSUV2Service osuv2Service;
//    @Autowired
//    BindingUtil bindingUtil;
//    @Autowired
//    CQService cqService;
//    @Autowired
//    ThreadPoolTaskExecutor threadPool;
//    @RequestMapping("${yumubot.osu-v2.path}")
//    @ResponseBody
//    public Object request(@RequestParam(name = "code") String code, @RequestParam(name = "state")String state){
//        String[] data = state.split(" ");
//        threadPool.execute(()->{
//            if(data.length != 0) {
//                BinUser bd = new BinUser(Long.valueOf(data[0]), code);
//                System.out.println(bd.toString());
//                osuv2Service.getToken(bd);
//                osuv2Service.getUserInfo(bd);
//                bindingUtil.Write(bd);
//                if (data.length == 2){
//                    cqService.sendGroupMsg(data[1], "成功绑定:" + bd.getQq() + "->" + bd.getOsuName());
//                }else {
//                    cqService.sendPrivateMsg(data[0], "成功绑定:" + bd.getQq() + "->" + bd.getOsuName());
//                    cqService.sendGroupMsg("7466713531", "成功绑定:" + bd.getQq() + "->" + bd.getOsuName());
//                }
//            }
//        });
//        return data[0]+"成功绑定！";
//    }
//}
