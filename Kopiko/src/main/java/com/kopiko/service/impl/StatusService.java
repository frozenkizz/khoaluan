/**
 * 
 */
package com.kopiko.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kopiko.entity.Status;
import com.kopiko.service.IStatusService;

/**
 * @author rianta9
 * @datecreated 23 thg 3, 2021 09:39:58
 */
@Service
public class StatusService implements IStatusService{

	@Override
	public List<Status> findAll() {
		List<Status> result = new ArrayList<Status>();
		result.add(new Status(1, "Hiển thị"));
		result.add(new Status(0, "Ẩn"));
		return result;
	}

}
