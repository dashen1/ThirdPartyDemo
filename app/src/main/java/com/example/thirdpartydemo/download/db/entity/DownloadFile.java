package com.example.thirdpartydemo.download.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class DownloadFile {
    @Id(autoincrement = true)
    Long id;
    @Unique
    int thread;
    Long start;
    Long end;
    Long process;


    @Generated(hash = 2125454566)
    public DownloadFile(Long id, int thread, Long start, Long end, Long process) {
        this.id = id;
        this.thread = thread;
        this.start = start;
        this.end = end;
        this.process = process;
    }

    @Generated(hash = 379234666)
    public DownloadFile() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getProcess() {
        return process;
    }

    public void setProcess(Long process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "DownloadFile{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", process=" + process +
                '}';
    }
}
