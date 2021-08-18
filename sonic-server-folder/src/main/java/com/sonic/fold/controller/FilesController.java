package com.sonic.fold.controller;

import com.sonic.common.config.WebAspect;
import com.sonic.common.http.RespModel;
import com.sonic.fold.tools.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final Logger logger = LoggerFactory.getLogger(FilesController.class);
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    @Autowired
    private FileTool fileTool;

    @WebAspect
    @DeleteMapping
    public RespModel delete(@RequestParam(name = "day") int day) {
        long timeMillis = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        List<String> fileList = Arrays.asList("imageFiles", "recordFiles", "logFiles");
        cachedThreadPool.execute(() -> {
            for (String fileType : fileList) {
                File[] type = new File(fileType).listFiles();
                for (File dateFile : type) {
                    try {
                        if (timeMillis - sf.parse(dateFile.getName()).getTime()
                                > day * 86400000L) {
                            logger.info("开始清理：" + dateFile.getPath());
                            fileTool.deleteDir(dateFile);
                        }
                    } catch (ParseException e) {
                        logger.info("文件名出错：" + dateFile.getPath());
                        logger.error(e.getMessage());
                    }
                }
            }
        });
        return new RespModel(2000, "开始清理！");
    }
}
