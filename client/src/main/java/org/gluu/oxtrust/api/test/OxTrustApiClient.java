package org.gluu.oxtrust.api.test;

public class OxTrustApiClient {

	public static void main(String[] args) throws Exception {
		GroupRepositoryImpl groupRepositoryImpl = new GroupRepositoryImpl();
		PeopleRepositoryImpl peopleRepositoryImpl = new PeopleRepositoryImpl();
		OxAuthClientRepositoryImpl oxAuthClientRepositoryImpl = new OxAuthClientRepositoryImpl();
		GluuScopeRepositoryImpl scopeRepositoryImpl = new GluuScopeRepositoryImpl();
		SectorIdentifierRepositoryImpl sectorIdentifierRepositoryImpl = new SectorIdentifierRepositoryImpl();
		groupRepositoryImpl.testAll();
		peopleRepositoryImpl.testAll();
		oxAuthClientRepositoryImpl.testAll();
		scopeRepositoryImpl.testAll();
		sectorIdentifierRepositoryImpl.testAll();
	}
}