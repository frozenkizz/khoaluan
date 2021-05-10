/**
 * 
 */
package com.kopiko.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kopiko.entity.Status;

/**
 * @author rianta9
 * @datecreated 23 thg 3, 2021 09:38:39
 */

@Service
public interface IStatusService {
	List<Status> findAll();
}
