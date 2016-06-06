package com.acetecsemi.godzilla.Godzilla.application.impl;

import java.sql.SQLException;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateCompanyLotApplication;

public class TestSubstrateCompanyLotApplicationImpl extends
		KoalaBaseSpringTestCase {

	@Inject
	private SubstrateCompanyLotApplication substrateCompanyLotApplication;

	@Before
	public void before() throws SQLException {
		// Statement stmt = null;
		// try{
		// String create =
		// " CREATE TABLE `Employee` ( `id` int(11) DEFAULT NULL, `name` varchar(255) DEFAULT NULL, `age` varchar(255) DEFAULT NULL, `birthDate` date DEFAULT NULL, `gender` varchar(255) DEFAULT NULL )";
		// DataSource dataSource =
		// InstanceFactory.getInstance(DataSource.class);
		// stmt = dataSource.getConnection().createStatement();
		// stmt.execute(create);
		// }catch(Exception e){
		// }finally{
		// stmt.close();
		// }
	}

	@Test
	public void test() {

		String lotNo = substrateCompanyLotApplication.getNewLotNo();
		System.out.println("lotNo : " + lotNo);
		// dayAttendanceConfirmationCreateApplication.createDayAttendanceConfirmation(0,
		// 5);
		// monthlyAttendanceConfirmationCreateApplication.createMonthlyAttendanceConfirmation(2014,
		// 5);
		// Assert.isTrue(pages.getPageCount()==2);
		// Assert.isTrue(pages.getStart()==10);
		// Assert.isTrue(pages.getData().size()==1);
	}
}