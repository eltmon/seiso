package com.expedia.seiso.web.assembler.impl;

import java.text.NumberFormat;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.NonNull;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.expedia.seiso.domain.entity.Node;
import com.expedia.seiso.domain.entity.ServiceInstance;
import com.expedia.seiso.domain.repo.ServiceRepo;
import com.expedia.seiso.web.assembler.ServiceService;
import com.expedia.seiso.web.resource.ServiceInstanceResource;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceRepo serviceRepo;

	private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

	static {
		percentFormat.setMinimumFractionDigits(1);
		percentFormat.setMaximumFractionDigits(1);
	}

	@Override
	public Resources<ServiceInstanceResource> getServiceInstances(@NonNull Long serviceId) {
		val service = serviceRepo.findOne(serviceId);
		val serviceInstances = service.getServiceInstances();
		val serviceServiceInstances = serviceInstances.stream().map(si -> toServiceInstanceResource(si))
				.collect(Collectors.toList());

		return new Resources<ServiceInstanceResource>(serviceServiceInstances);
	}

	private ServiceInstanceResource toServiceInstanceResource(ServiceInstance serviceInstance) {

		val siNodes = serviceInstance.getNodes();
		val siResource = new ServiceInstanceResource();
		val numNodes = siNodes.size();
		int numHealthy = 0;
		for (Node siN : siNodes) {
			String healthKey = siN.getHealthStatus().getStatusType().getStatusType() != null ? siN.getHealthStatus().getStatusType().getKey() : "unknown";
			// TODO: There are more cases in which a node deemed healthy.
			if (healthKey.equalsIgnoreCase("Healthy"))
				numHealthy++;
		}

		String percentHealthy = (numNodes == 0L ? "N/A"
				: percentFormat.format((double) numHealthy / (double) numNodes));

		String statusKey = ((numHealthy < numNodes || numNodes == 0) ? "warning" : "success");
		siResource.setKey(serviceInstance.getKey());
		siResource.setNumNodes(numNodes);
		siResource.setHealthKey(statusKey);
		siResource.setNumHealthy(numHealthy);
		siResource.setPercentHealthy(percentHealthy);

		return siResource;
	}

}
