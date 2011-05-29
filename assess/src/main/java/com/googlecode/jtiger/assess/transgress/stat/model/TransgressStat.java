package com.googlecode.jtiger.assess.transgress.stat.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * 违法统计表
 */
import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.cms.article.model.Article;
import com.googlecode.jtiger.modules.security.user.model.User;

@SuppressWarnings("serial")
@Entity
@Table(name = "transgress_stat")
public class TransgressStat extends BaseIdModel {
	/** 统计日期 */
	private Date statTime;
	/** 统计开始时间 */
	private Date beginTime;
	/** 统计结束时间 */
	private Date endTime;
	/** 统计人员 */
	private User statUser;
	/** 统计部门 */
	private Article article;

	private Set<TransgressStatDetail> transgressStatDetails = new HashSet<TransgressStatDetail>(
			0);

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transgressStat", cascade = { CascadeType.MERGE })
	public Set<TransgressStatDetail> getTransgressStatDetails() {
		return transgressStatDetails;
	}

	public void setTransgressStatDetails(
			Set<TransgressStatDetail> transgressStatDetails) {
		this.transgressStatDetails = transgressStatDetails;
	}

	public Date getStatTime() {
		return statTime;
	}

	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

	@ManyToOne
	@JoinColumn(name = "stat_user_id")
	public User getStatUser() {
		return statUser;
	}

	public void setStatUser(User statUser) {
		this.statUser = statUser;
	}
}
