This is a project for demonstrating the second-killing activity  typically used by e-commerce shop promotion.<br /><br />

It is based on [Spring Boot](http://projects.spring.io/spring-boot/), and making use of redis for the persistence.<br />
 * use Redisson AtomicLong to filter the 1st layer - the number of requests can be pass thourgh is not more than the count of the gift. <br />
 * use Redisson distributed Lock to filter the 2nd layer - only the use who has not been got gift can participate the acitivity, that's, can pass through to the persistency layer.<br />
    
##1. install redis
 wget http://download.redis.io/releases/redis-3.0.3.tar.gz<br />
 tar xzf redis-3.0.3.tar.gz<br />
 cd redis-3.0.3<br />
 make<br />
 src/redis-server<br />
 \# another console <br />, to clean up the database for re-run the test, <b>PLEASE NOTE THAT IT WILL EMPTY ALL OF THE DATABASE</b>.
 src/redis-cli flushall
 
##2. install wrk
 git clone https://github.com/wg/wrk.git<br />
 cd wrk<br />
 make<br />
 
##3. run demo
git clone https://github.com/pizhigang/demo-gift.git<br />
cd demo-gift<br />
mvn clean package<br />
java -jar target/demo-1.0-SNAPSHOT.jar --redis.ip=localhost --redis.port=6379 --gift.count=10000<br />
 
##4. load test 
\# mock DDos - This runs a benchmark for 10 seconds, using 4 threads, and keeping 500 HTTP connections open.<br />
wrk -t4 -c500 -d10s http://localhost:8080/gift/<br />

\# the normal case - one use can only get gift once<br />
wrk -t4 -c100 -d2s http://localhost:8080/gift/1<br />
