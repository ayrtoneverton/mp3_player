package com.player.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import java.io.*;

import com.player.model.Music;
import com.player.model.Persist;
import com.player.model.PersistListWrapper;
import com.player.model.PlayList;
import com.player.model.User;

public class DAO {

	public static User findActiveUser(List<User> list){
		try {
			String id = new String(Files.readAllBytes(Paths.get("active.txt")));
			if(!id.isEmpty())
			for(User u: list) {
				if(Util.toMD5(u.getId()).equals(id))
					return u;
			}
		} catch (Exception e) {}
		return null;
	}

	public static void saveActiveUser(User user){
		try {
			if(user != null) {
				PrintWriter txt = new PrintWriter(new File("active.txt"));
				txt.print(Util.toMD5(user.getId()));
				txt.flush();
				txt.close();
			}else {
				new File("active.txt").delete();
			}
		} catch (Exception e) {}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Persist> List<T> findAll(Class<T> classe){
		try {
			return ((PersistListWrapper<T>) JAXBContext
						.newInstance(PersistListWrapper.class, User.class, Music.class, PlayList.class)
						.createUnmarshaller()
						.unmarshal(new StreamSource(classe.getSimpleName().toLowerCase()+".xml"), PersistListWrapper.class).getValue()).getPersists();
		} catch (Exception e) {
			return new ArrayList<T>();
		}
	}

	@SuppressWarnings("rawtypes")
	public static <T extends Persist> void saveAll(List<T> list){
		try {
			String name = list.get(0).getClass().getSimpleName().toLowerCase();
			Marshaller m = JAXBContext.newInstance(PersistListWrapper.class, User.class, Music.class, PlayList.class).createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(new JAXBElement<PersistListWrapper>(new QName(name), PersistListWrapper.class, new PersistListWrapper<T>(list)), new File(name+".xml"));
		} catch (Exception e) {}
	}
}
