package com.cg.uas.dao;

import com.cg.uas.bean.Participant;
import com.cg.uas.exception.NoSuchParticipant;
import com.cg.uas.exception.ParticipantAlreadyExistsException;

public interface ParticipantDao {//shailesh
	Participant readParticipant(String rollNo) throws NoSuchParticipant;

	boolean createParticipant(Participant p) throws ParticipantAlreadyExistsException;

	boolean updateParticipant(String rollNo, Participant p) throws NoSuchParticipant;

	boolean deleteParticipant(String rollNo) throws NoSuchParticipant;
}
