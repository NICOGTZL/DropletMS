package com.nicogtzl.CloudServices;

public interface CloudService {
    void createInstance(Task task);
    void destroyInstance(Task task);
}