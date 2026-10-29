package at.sds.wm;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("dev")
public class H2TcpConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer(
                "-tcp",
                "-tcpPort", "9092",
                "-tcpDaemon"
        );
    }
}
