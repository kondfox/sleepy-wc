package com.gfa.wc;

import com.gfa.wc.utils.DBInit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WcApplication implements CommandLineRunner {

	private DBInit dbInit;

	public WcApplication(DBInit dbInit) {
		this.dbInit = dbInit;
	}

	public static void main(String[] args) {
		SpringApplication.run(WcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (Boolean.parseBoolean(System.getenv("WC_TEAMS_INIT"))) {
			dbInit.initTeams();
		}
		dbInit.initMatches();
	}

}
