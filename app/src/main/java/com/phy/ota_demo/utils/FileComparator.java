package com.phy.ota_demo.utils;

import com.phy.ota_demo.bean.UpgradeFile;

import java.util.Comparator;

public class FileComparator implements Comparator<UpgradeFile> {
    @Override
    public int compare(UpgradeFile file1, UpgradeFile file2) {
        if (file1.getFile().lastModified() > file2.getFile().lastModified()) {
            return -1;
        } else {
            return 1;
        }
    }
}
