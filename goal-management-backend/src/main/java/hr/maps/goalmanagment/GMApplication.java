package hr.maps.goalmanagment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import hr.maps.goalmanagment.config.FileStorageProperties;
import hr.maps.goalmanagment.enumeration.RoleCode;
import hr.maps.goalmanagment.persistence.entities.Country;
import hr.maps.goalmanagment.persistence.entities.Role;
import hr.maps.goalmanagment.persistence.repositories.CityRepository;
import hr.maps.goalmanagment.persistence.repositories.CountryRepository;
import hr.maps.goalmanagment.persistence.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
@EnableConfigurationProperties({ FileStorageProperties.class })
public class GMApplication implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final CountryRepository countryRepository;
	private final CityRepository cityRepository;


	public static void main(String[] args) {
		SpringApplication.run(GMApplication.class, args);
		log.info("------------ The GM APP server was sucessuflly started ---");
	}

	@Override
	public void run(String... arg0) {
		log.info("------------ PROCESS TO EXECUTE WHEN STARTING THE SERVER  ---");
		Role adminRole = this.roleRepository.findByRoleCode(RoleCode.ADMINISTRATOR);
		if (adminRole == null) {
			adminRole = new Role();
			adminRole.setRoleCode(RoleCode.ADMINISTRATOR);
			adminRole.setRoleLabel("ADMINISTRATEUR");
			this.roleRepository.save(adminRole);
		}
		Role lodgerRole = this.roleRepository.findByRoleCode(RoleCode.COLLABORATOR);
		if (lodgerRole == null) {
			lodgerRole = new Role();
			lodgerRole.setRoleCode(RoleCode.COLLABORATOR);
			lodgerRole.setRoleLabel("COLLABORATEUR");
			this.roleRepository.save(lodgerRole);
		}
		Role ownerRole = this.roleRepository.findByRoleCode(RoleCode.MANAGER);
		if (ownerRole == null) {
			ownerRole = new Role();
			ownerRole.setRoleCode(RoleCode.MANAGER);
			ownerRole.setRoleLabel("MANAGER");
			this.roleRepository.save(ownerRole);
		}

		Country tnCountry = this.countryRepository.findByCountryCode("TN");
		if (tnCountry == null) {
			tnCountry = new Country();
			tnCountry.setCountryCode("TN");
			tnCountry.setCountryLabel("Tunisie");
			tnCountry = this.countryRepository.save(tnCountry);
		}

	}
}