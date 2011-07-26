package com.googlecode.jtiger.assess.task.statcfg.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.modules.quartz.model.Cron;

//@Service
//@Lazy(false)
public class AssessCronInitializer {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	//@Autowired(required = true)
	private SessionFactory sessionFactory;

	//@PostConstruct
	//@Transactional
	public void init() {
		Session session = sessionFactory.openSession();
		if (!hasAssessCron(session)) {
			Transaction tx = session.beginTransaction();
			Cron cron = new Cron();
			try {
				cron.setName(AssessConstants.CRON_ASSESS);
				cron.setMarker(AssessConstants.CRON_ASSESS);
				cron.setCron(AssessConstants.CRON_ASSESS_DEFAULT);
				session.save(cron);
			} finally {
				session.flush();
				tx.commit();
				session.close();
			}
		}

	}

	/**
	 * 判断是否已经存在AssessCron
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean hasAssessCron(Session session) {
		List list = session.createQuery(
				"from Cron c where c.marker = " + "'"
						+ AssessConstants.CRON_ASSESS + "'").list();

		return list != null && list.size() > 0;
	}
}
