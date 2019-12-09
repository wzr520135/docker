package docker;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther wzr
 * @create 2019-12-09 16:18
 * @Description
 * @return
 */

public class TestRedis {

  @Test
    public  void test01(){

        Jedis jedis=new Jedis("192.168.198.5", 7000);
         jedis.set("key1", "value1");
               String  s=jedis.get("key1");
        System.out.println(s);
        jedis.close();;
    }
    /**
    * @Author: wzr
    * @Date: 2019/12/9 16:28
    * @Description:

       redis分片
     */
    @Test
     public  void test02(){
         //配置线程池
        JedisPoolConfig  cfg =new JedisPoolConfig();
        cfg.setMaxTotal(500);
        cfg.setMaxIdle(20);

        List<JedisShardInfo> shards=new ArrayList<>();
         shards.add(new JedisShardInfo("192.168.198.5", 7000));
         shards.add(new JedisShardInfo("192.168.198.5", 7001));
         shards.add(new JedisShardInfo("192.168.198.5", 7002));
          //创建线程池
         ShardedJedisPool pool=new ShardedJedisPool(cfg, shards);
           ShardedJedis jedis=pool.getResource();  //通过线程池获取jedis
        for (int i = 0; i <100 ; i++) {
               jedis.set("key"+i, "value"+i);
        }

          pool.close();
    }


}

