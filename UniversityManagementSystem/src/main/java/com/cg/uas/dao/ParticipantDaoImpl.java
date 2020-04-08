package com.cg.uas.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.uas.bean.Participant;
import com.cg.uas.exception.NoSuchParticipant;
import com.cg.uas.exception.ParticipantAlreadyExistsException;

public class ParticipantDaoImpl implements ParticipantDao {
	private static HashMap<String, Participant> participants = new HashMap<String, Participant>();
	private static final Logger logger = Logger.getLogger(ApplicationDaoImpl.class);

	static {
		PropertyConfigurator.configure("src/main/resources/log4j/log4j.properties");
	}

	@Override
	public Participant readParticipant(String rollNo) throws NoSuchParticipant {
		Participant p = participants.get(rollNo);
		if (p == null) {
			logger.info("Participant with roll no: " + rollNo + " was found");
			return p;
		} else {
			logger.info("Participant with roll no: " + rollNo + " was not found");
			throw new NoSuchParticipant();
		}
	}

	@Override
	public boolean createParticipant(Participant p) throws ParticipantAlreadyExistsException {
		Participant result = participants.putIfAbsent(p.getRollNo(), p);
		if (result == null) {
			logger.info("Participant with roll no: " + p.getRollNo() + " was created");
			return true;
		} else {
			logger.info("Participant with roll no: " + p.getRollNo() + " already exists");
			throw new ParticipantAlreadyExistsException();

		}
	}

	@Override
	public boolean updateParticipant(String rollNo, Participant p) {
		if (participants.containsKey(rollNo)) {
			Participant result = participants.put(rollNo, p);

			if (result != null) {
				logger.info("Participant with roll no: " + p.getRollNo() + " was updated");
				return true;
			} else {
				logger.info("Participant with roll no: " + p.getRollNo() + " update failed");
				return false;
			}
		} else {
			logger.info("Participant with roll no: " + rollNo + " not found");
			return false;
		}

	}

	@Override
	public boolean deleteParticipant(String rollNo) throws NoSuchParticipant {
		if (participants.containsKey(rollNo)) {
			Participant p = participants.remove(rollNo);
			if (participants.containsKey(rollNo)) {
				logger.info("Participant with roll no: " + p.getRollNo() + " was not deleted.");
				return false;
			} else {
				logger.info("Participant with roll no: " + p.getRollNo() + " was deleted.");
				return false;
			}
		} else {
			logger.info("Participant with roll no: " + rollNo + " not found");
			throw new NoSuchParticipant();
		}
	}

	// mockdata
	// to insert data
	public void mockData() {
		participants.put("1001", new Participant("1001", "siddharth@gmail.com", "3", "A1003"));
		participants.put("1002", new Participant("1002", "siddharth2@gmail.com", "4", "A1004"));
	}

	// to print full map
	public void print() {
		System.out.println(participants);
	}

	@Override
	public ArrayList<Participant> getAll() {
		List<Participant> result = participants.values().stream().collect(Collectors.toList());
		return new ArrayList<Participant>(result);
	}

}
