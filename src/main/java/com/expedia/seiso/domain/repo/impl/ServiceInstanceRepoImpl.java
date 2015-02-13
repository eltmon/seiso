/* 
 * Copyright 2013-2015 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expedia.seiso.domain.repo.impl;

import java.util.Collections;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.expedia.seiso.domain.entity.ServiceInstance;
import com.expedia.seiso.domain.repo.custom.ServiceInstanceRepoCustom;

/**
 * @author Willie Wheeler
 */
public class ServiceInstanceRepoImpl implements ServiceInstanceRepoCustom {
	private static final String ENTITY_NAME = "ServiceInstance";
	private static final Set<String> FIELD_NAMES = Collections.singleton("key");
	
	@PersistenceContext private EntityManager entityManager;
	@Autowired private RepoImplUtils repoUtils;

	@Override
	public Class<ServiceInstance> getResultType() { return ServiceInstance.class; }

	@Override
	public Page<ServiceInstance> search(Set<String> searchTokens, Pageable pageable) {
		return repoUtils.search(ENTITY_NAME, entityManager, FIELD_NAMES, searchTokens, pageable);
	}
}
