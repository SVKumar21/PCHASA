import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
// import java.util.Collections;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.*;

class Main {
    static int refresh;
    static String time;
    static String sch;
    static Main obj;

    // staic methods
    public static Connection dbConnect() {
        refresh = 0;
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:MainDB.db");
        } catch (Exception sqlErr) {
            JOptionPane.showMessageDialog(null, "Failed to connect Database!", "VNRVJIET", JOptionPane.ERROR_MESSAGE);
        }
        return c;
    }

    public static void deleteRow(String slotID) {
        try {
            int id = Integer.parseInt(slotID);
            Class.forName("org.sqlite.JDBC");

            Connection con = dbConnect();
            Statement stmt = con.createStatement();
            String Q = "delete from user_data where slot_id= " + id + "";

            stmt.execute(Q);
            stmt.close();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Failed to connect Database!", "VNRVJIET", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void DaysGone() {
        try {
            LocalDate localDate = LocalDate.now();
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            int curdayno = dayOfWeek.getValue();
            Class.forName("org.sqlite.JDBC");
            Connection con = dbConnect();
            Statement stmt = con.createStatement();
            String Q = "delete from user_data where day_no <" + curdayno + "";
            stmt.execute(Q);
            stmt.close();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Failed to connect Database!", "VNRVJIET", JOptionPane.ERROR_MESSAGE);
        }
    }

    ///
    Main() {
        JFrame frame;
        frame = new JFrame();
        frame.setTitle("NIXAT");
        ImageIcon logo = new ImageIcon("roar.jpg");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(Color.decode("#08FC9A"));
        frame.setLocationRelativeTo(null);

        ///
        JLabel headingLabel = new JLabel(
                "<html><b><h6 style='color:grey; font-size:30px;'>VNR VJIET GROUP OF HOSPITALS</h6><b></html>");
        headingLabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        headingLabel.setBounds(240, 10, 979, 60);
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(headingLabel);
        ///
        JLabel QuestionL = new JLabel("<html><b><h2 style='color:grey;'>WELCOME USER!</h2><b></html>");
        QuestionL.setFont(new Font("Arial", Font.ROMAN_BASELINE, 20));
        QuestionL.setBounds(480, 200, 500, 60);
        QuestionL.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(QuestionL);
        //
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBackground(Color.decode("#c2fbd7")); // Set the background color to white
        Panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        frame.getContentPane().add(Panel);
        // IPR
        // IPR vnrvjiet
        JLabel IRP = new JLabel("<html><b><h4 style='color:grey;'>© All rights reserved @vnrvjiet</h4><b></html>");
        IRP.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        IRP.setBounds(490, 780, 500, 100);
        IRP.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(IRP);
        JButton Need = new JButton("Health care");
        JButton Doctor = new JButton("Doctor");
        JButton BookNow = new JButton("Select Time Slot");
        JButton GoWalkIn = new JButton("Go Walk In");

        // collage Logo
        ImageIcon clglogo = new ImageIcon("clg_logo.png");
        Image iclglogo = clglogo.getImage();
        Image resizedImg = iclglogo.getScaledInstance(170, 150, Image.SCALE_SMOOTH);
        ImageIcon reclglogo = new ImageIcon(resizedImg);
        JLabel img = new JLabel(reclglogo);
        img.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        img.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                refresh = 0;
                img.setToolTipText("Refresh VNR VJIET");
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                refresh = 0;
                img.setToolTipText("");
                super.mouseExited(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                refresh += 1;
                if (refresh == 2) {
                    int choosen = JOptionPane.showConfirmDialog(null,
                            "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Do you want to refresh the window?</h4></b></html>",
                            "VNR VJIET", JOptionPane.YES_NO_OPTION);
                    if (choosen == JOptionPane.YES_OPTION) {
                        frame.dispose();
                        refresh = 0;
                        obj = new Main();
                    }
                }
            }
        });
        img.setBounds(1355, 10, 150, 150);
        frame.getContentPane().add(img);

        // WHO logo
        ImageIcon healthlogo = new ImageIcon("WHO.png");
        Image ihealthlogo = healthlogo.getImage();
        Image resizedhealthlogo = ihealthlogo.getScaledInstance(170, 150, Image.SCALE_SMOOTH);
        ImageIcon rehealthlogo = new ImageIcon(resizedhealthlogo);
        JLabel health_img = new JLabel(rehealthlogo);

        health_img.setBounds(35, 10, 150, 150);
        frame.getContentPane().add(health_img);

        // slots Logo
        ImageIcon slot = new ImageIcon("slots.png");
        Image islot = slot.getImage();
        Image resizedslot = islot.getScaledInstance(170, 150, Image.SCALE_SMOOTH);
        ImageIcon reslot = new ImageIcon(resizedslot);
        JLabel slot_img = new JLabel(reslot);
        slot_img.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        slot_img.setBounds(950, 305, 40, 40);

        slot_img.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                slot_img.setToolTipText("Slots");
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                slot_img.setToolTipText("");
                super.mouseExited(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                try {
                    Class.forName("org.sqlite.JDBC");

                    Connection con = dbConnect();
                    Statement stmt = con.createStatement();
                    String Q = "select slot_id from user_data";
                    ResultSet rs = stmt.executeQuery(Q);
                    List<Integer> slots_over = new ArrayList<>();
                    while (rs.next()) {
                        int id = rs.getInt("slot_id");
                        slots_over.add(id);
                    }

                    stmt.close();

                    frame.remove(Need);
                    frame.remove(Doctor);
                    frame.remove(QuestionL);
                    frame.remove(slot_img);

                    //

                    Panel.removeAll();
                    frame.repaint();
                    frame.revalidate();
                    Panel.setBounds(250, 150, 980, 550);
                    //

                    JButton Back = new JButton("<html><h2>Back</h2></html>");
                    Back.setBounds(10, 500, 80, 40);
                    Back.setBackground(Color.decode("#128800"));
                    Back.setForeground(Color.WHITE);
                    ActionListener actionListenerBack = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                            frame.dispose();
                            obj = new Main();
                        };
                    };
                    Back.addActionListener(actionListenerBack);
                    Panel.add(Back);

                    JLabel description = new JLabel(
                            "<html><h2>Red slots are already booked by someone else.!!</h2></html>");
                    description.setBounds(280, 500, 500, 40);
                    Panel.add(description);

                    JButton team = new JButton("<html><h2>About</h2></html>");
                    team.setBounds(870, 500, 100, 40);
                    team.setBackground(Color.decode("#128800"));
                    team.setForeground(Color.WHITE);
                    ActionListener actionListenerteam = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Panel.removeAll();
                            Panel.repaint();
                            frame.repaint();
                            frame.revalidate();
                            JLabel team_description = new JLabel(
                                    "<html><h3><u>Patient-Centric Hospital Appointment Scheduling System</u> - VNR VJIET</h3>\r\n"
                                            + //
                                            "\r\n" + //
                                            "<h5>In the realm of operating systems, we a dynamic team of four talented individuals, Varshit, Nisritha, Keerthana, and Sathish, collaboratively embarked on a transformative project named \"Patient-Centric Hospital Appointment Scheduling System.\" The project, a culmination of our efforts in an Operating System course, serves as a testament to our dedication and proficiency.</h5>\r\n"
                                            + //
                                            "\r\n" + //
                                            "<h3><u>Algorithms at Play:</u></h3><h5>FCFS and Priority Scheduling</h5>\r\n"
                                            + //
                                            "<h3><u>Technological Backbone:</u></h5><h5>Java Swings and Applets</h5>\r\n"
                                            + //
                                            "<h3><u>DataBase Management:</u></h3><h5>SQLite3.4 Version</h5>\r\n" + //
                                            "\r\n" + //
                                            "<h3><u>Faculty Mentorship:</u></h3>\r\n" + //
                                            "<h5>Shaik Abdul Hameed,</h5>\r\n" + //
                                            "<h5>Saddam Hussain</h5>\r\n" + //
                                            "\r\n" + //
                                            "\r\n" + //
                                            "<h3><u>Team Members:</u></h3>\r\n" + //
                                            "<h5>Keerthana,\r\n" + //
                                            "Nisritha,\r\n" + //
                                            "Varshit Kumar,\r\n" + //
                                            "Sathish.</h5></html>");
                            team_description.setBounds(10, 10, 990, 540);
                            Panel.add(team_description);
                            Panel.repaint();
                            frame.repaint();
                            frame.revalidate();
                        };
                    };
                    team.addActionListener(actionListenerteam);
                    Panel.add(team);

                    //

                    int x = 10;
                    // col-0
                    JButton M = new JButton("MON");
                    M.setBounds(x, 10, 80, 80);
                    M.setBackground(new Color(52, 2, 29));
                    M.setForeground(Color.BLACK);
                    M.setEnabled(false);
                    Panel.add(M);

                    JButton T = new JButton("TUE");
                    T.setBounds(x, 90, 80, 80);
                    T.setBackground(new Color(52, 2, 29));
                    T.setForeground(Color.BLACK);
                    T.setEnabled(false);
                    Panel.add(T);
                    JButton W = new JButton("WED");
                    W.setBounds(x, 170, 80, 80);
                    W.setBackground(new Color(52, 2, 29));
                    W.setForeground(Color.BLACK);
                    W.setEnabled(false);
                    Panel.add(W);
                    JButton TH = new JButton("THU");
                    TH.setBounds(x, 250, 80, 80);
                    TH.setBackground(new Color(52, 2, 29));
                    TH.setForeground(Color.BLACK);
                    TH.setEnabled(false);
                    Panel.add(TH);
                    JButton F = new JButton("FRI");
                    F.setBounds(x, 330, 80, 80);
                    F.setBackground(new Color(52, 2, 29));
                    F.setForeground(Color.BLACK);
                    F.setEnabled(false);
                    Panel.add(F);
                    JButton S = new JButton("SAT");
                    S.setBounds(x, 410, 80, 80);
                    S.setBackground(new Color(52, 2, 29));
                    S.setForeground(Color.BLACK);
                    S.setEnabled(false);
                    Panel.add(S);

                    // col1
                    JButton M1 = new JButton("10am-11am");
                    M1.setBounds(100, 10, 100, 80);
                    ActionListener actionListenerM1 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "1";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            M1.setEnabled(false);
                            M1.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    M1.setEnabled(false);
                    M1.addActionListener(actionListenerM1);
                    Panel.add(M1);
                    JButton T1 = new JButton("10am-11am");
                    T1.setBounds(100, 90, 100, 80);
                    ActionListener actionListenerT1 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "8";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            T1.setEnabled(false);
                            T1.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    T1.setEnabled(false);
                    T1.addActionListener(actionListenerT1);
                    Panel.add(T1);
                    JButton W1 = new JButton("10am-11am");
                    W1.setBounds(100, 170, 100, 80);
                    ActionListener actionListenerW1 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "15";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            W1.setEnabled(false);
                            W.setBackground(null);
                            deleteRow(time);
                        };

                    };
                    W1.setEnabled(false);
                    W1.addActionListener(actionListenerW1);
                    Panel.add(W1);
                    JButton TH1 = new JButton("10am-11am");
                    TH1.setBounds(100, 250, 100, 80);
                    ActionListener actionListenerTH1 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "22";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            TH1.setEnabled(false);
                            TH1.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    TH1.setEnabled(false);
                    TH1.addActionListener(actionListenerTH1);
                    Panel.add(TH1);
                    JButton F1 = new JButton("10am-11am");
                    F1.setBounds(100, 330, 100, 80);
                    ActionListener actionListenerF1 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "29";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            F1.setEnabled(false);
                            F1.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    F1.setEnabled(false);
                    F1.addActionListener(actionListenerF1);
                    Panel.add(F1);
                    JButton S1 = new JButton("10am-11am");
                    S1.setBounds(100, 410, 100, 80);
                    ActionListener actionListenerS1 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "36";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            S1.setEnabled(false);
                            S1.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    S1.setEnabled(false);
                    S1.addActionListener(actionListenerS1);
                    Panel.add(S1);

                    // col-2
                    JButton M2 = new JButton("11am-12pm");
                    M2.setBounds(210, 10, 100, 80);
                    ActionListener actionListenerM2 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "2";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            M2.setEnabled(false);
                            M2.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    M2.setEnabled(false);
                    M2.addActionListener(actionListenerM2);
                    Panel.add(M2);
                    JButton T2 = new JButton("11am-12pm");
                    T2.setBounds(210, 90, 100, 80);
                    ActionListener actionListenerT2 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "9";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            T2.setEnabled(false);
                            T2.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    T2.setEnabled(false);
                    T2.addActionListener(actionListenerT2);
                    Panel.add(T2);
                    JButton W2 = new JButton("11am-12pm");
                    W2.setBounds(210, 170, 100, 80);
                    ActionListener actionListenerW2 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "16";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            W2.setEnabled(false);
                            W2.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    W2.setEnabled(false);
                    W2.addActionListener(actionListenerW2);
                    Panel.add(W2);
                    JButton TH2 = new JButton("11am-12pm");
                    TH2.setBounds(210, 250, 100, 80);
                    ActionListener actionListenerTH2 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "23";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            TH2.setEnabled(false);
                            TH2.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    TH2.setEnabled(false);
                    TH2.addActionListener(actionListenerTH2);
                    Panel.add(TH2);
                    JButton F2 = new JButton("11am-12pm");
                    F2.setBounds(210, 330, 100, 80);
                    ActionListener actionListenerF2 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "30";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            F2.setEnabled(false);
                            F2.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    F2.setEnabled(false);
                    F2.addActionListener(actionListenerF2);
                    Panel.add(F2);
                    JButton S2 = new JButton("11am-12pm");
                    S2.setBounds(210, 410, 100, 80);
                    ActionListener actionListenerS2 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "37";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            S2.setEnabled(false);
                            S2.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    S2.setEnabled(false);
                    S2.addActionListener(actionListenerS2);
                    Panel.add(S2);
                    // col-3
                    JButton M3 = new JButton("12pm-01pm");
                    M3.setBounds(320, 10, 100, 80);
                    ActionListener actionListenerM3 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "3";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            M3.setEnabled(false);
                            M3.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    M3.setEnabled(false);
                    M3.addActionListener(actionListenerM3);
                    Panel.add(M3);
                    JButton T3 = new JButton("12pm-01pm");
                    T3.setBounds(320, 90, 100, 80);
                    ActionListener actionListenerT3 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "10";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            T3.setEnabled(false);
                            T3.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    T3.setEnabled(false);
                    T3.addActionListener(actionListenerT3);
                    Panel.add(T3);
                    JButton W3 = new JButton("12pm-01pm");
                    W3.setBounds(320, 170, 100, 80);
                    ActionListener actionListenerW3 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "17";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            W3.setEnabled(false);
                            W3.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    W3.setEnabled(false);
                    W3.addActionListener(actionListenerW3);
                    Panel.add(W3);
                    JButton TH3 = new JButton("12pm-01pm");
                    TH3.setBounds(320, 250, 100, 80);
                    ActionListener actionListenerTH3 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "24";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            TH3.setEnabled(false);
                            TH3.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    TH3.setEnabled(false);
                    TH3.addActionListener(actionListenerTH3);
                    Panel.add(TH3);
                    JButton F3 = new JButton("12pm-01pm");
                    F3.setBounds(320, 330, 100, 80);
                    ActionListener actionListenerF3 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "31";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            F3.setEnabled(false);
                            F3.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    F3.setEnabled(false);
                    F3.addActionListener(actionListenerF3);
                    Panel.add(F3);
                    JButton S3 = new JButton("12pm-01pm");
                    S3.setBounds(320, 410, 100, 80);
                    ActionListener actionListenerS3 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "38";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            S3.setEnabled(false);
                            S3.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    S3.setEnabled(false);
                    S3.addActionListener(actionListenerS3);
                    Panel.add(S3);
                    // col-4
                    JButton M4 = new JButton("01pm-02pm");
                    M4.setBounds(430, 10, 100, 80);
                    ActionListener actionListenerM4 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "4";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            M4.setEnabled(false);
                            M4.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    M4.setEnabled(false);
                    M4.addActionListener(actionListenerM4);
                    Panel.add(M4);
                    JButton T4 = new JButton("01pm-02pm");
                    T4.setBounds(430, 90, 100, 80);
                    ActionListener actionListenerT4 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "11";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            T4.setEnabled(false);
                            T4.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    T4.setEnabled(false);
                    T4.addActionListener(actionListenerT4);
                    Panel.add(T4);
                    JButton W4 = new JButton("01pm-02pm");
                    W4.setBounds(430, 170, 100, 80);
                    ActionListener actionListenerW4 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "18";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            W4.setEnabled(false);
                            W4.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    W4.setEnabled(false);
                    W4.addActionListener(actionListenerW4);
                    Panel.add(W4);
                    JButton TH4 = new JButton("01pm-02pm");
                    TH4.setBounds(430, 250, 100, 80);
                    ActionListener actionListenerTH4 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "25";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            TH4.setEnabled(false);
                            TH4.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    TH4.setEnabled(false);
                    TH4.addActionListener(actionListenerTH4);
                    Panel.add(TH4);
                    JButton F4 = new JButton("01pm-02pm");
                    F4.setBounds(430, 330, 100, 80);
                    ActionListener actionListenerF4 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "32";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            F4.setEnabled(false);
                            F4.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    F4.setEnabled(false);
                    F4.addActionListener(actionListenerF4);
                    Panel.add(F4);
                    JButton S4 = new JButton("01pm-02pm");
                    S4.setBounds(430, 410, 100, 80);
                    ActionListener actionListenerS4 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "39";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            S4.setEnabled(false);
                            S4.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    S4.setEnabled(false);
                    S4.addActionListener(actionListenerS4);
                    Panel.add(S4);
                    // col-5
                    JButton M5 = new JButton("Break");
                    M5.setBounds(540, 10, 100, 80);
                    M5.setEnabled(false);
                    Panel.add(M5);
                    JButton T5 = new JButton("Break");
                    T5.setBounds(540, 90, 100, 80);
                    T5.setEnabled(false);
                    Panel.add(T5);
                    JButton W5 = new JButton("Break");
                    W5.setBounds(540, 170, 100, 80);
                    W5.setEnabled(false);
                    Panel.add(W5);
                    JButton TH5 = new JButton("Break");
                    TH5.setBounds(540, 250, 100, 80);
                    TH5.setEnabled(false);
                    Panel.add(TH5);
                    JButton F5 = new JButton("Break");
                    F5.setBounds(540, 330, 100, 80);
                    F5.setEnabled(false);
                    Panel.add(F5);
                    JButton S5 = new JButton("Break");
                    S5.setBounds(540, 410, 100, 80);
                    S5.setEnabled(false);
                    Panel.add(S5);
                    // col-6
                    JButton M6 = new JButton("03pm-04pm");
                    M6.setBounds(650, 10, 100, 80);
                    ActionListener actionListenerM6 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "5";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            M6.setEnabled(false);
                            M6.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    M6.setEnabled(false);
                    M6.addActionListener(actionListenerM6);
                    Panel.add(M6);
                    JButton T6 = new JButton("03pm-04pm");
                    T6.setBounds(650, 90, 100, 80);
                    ActionListener actionListenerT6 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "12";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            T6.setEnabled(false);
                            T6.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    T6.setEnabled(false);
                    T6.addActionListener(actionListenerT6);
                    Panel.add(T6);
                    JButton W6 = new JButton("03pm-04pm");
                    W6.setBounds(650, 170, 100, 80);
                    ActionListener actionListenerW6 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "19";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            W6.setEnabled(false);
                            W6.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    W6.setEnabled(false);
                    W6.addActionListener(actionListenerW6);
                    Panel.add(W6);
                    JButton TH6 = new JButton("03pm-04pm");
                    TH6.setBounds(650, 250, 100, 80);
                    ActionListener actionListenerTH6 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "26";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            TH6.setEnabled(false);
                            TH6.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    TH6.setEnabled(false);
                    TH6.addActionListener(actionListenerTH6);
                    Panel.add(TH6);
                    JButton F6 = new JButton("03pm-04pm");
                    F6.setBounds(650, 330, 100, 80);
                    ActionListener actionListenerF6 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "33";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            F6.setEnabled(false);
                            F6.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    F6.setEnabled(false);
                    F6.addActionListener(actionListenerF6);
                    Panel.add(F6);
                    JButton S6 = new JButton("03pm-04pm");
                    S6.setBounds(650, 410, 100, 80);
                    ActionListener actionListenerS6 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "40";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            S6.setEnabled(false);
                            S6.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    S6.setEnabled(false);
                    S6.addActionListener(actionListenerS6);
                    Panel.add(S6);
                    // col-7
                    JButton M7 = new JButton("04pm-05pm");
                    M7.setBounds(760, 10, 100, 80);
                    ActionListener actionListenerM7 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "6";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            M7.setEnabled(false);
                            M7.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    M7.setEnabled(false);
                    M7.addActionListener(actionListenerM7);
                    Panel.add(M7);
                    JButton T7 = new JButton("04pm-05pm");
                    T7.setBounds(760, 90, 100, 80);
                    ActionListener actionListenerT7 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "13";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            T7.setEnabled(false);
                            T7.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    T7.setEnabled(false);
                    T7.addActionListener(actionListenerT7);
                    Panel.add(T7);
                    JButton W7 = new JButton("04pm-05pm");
                    W7.setBounds(760, 170, 100, 80);
                    ActionListener actionListenerW7 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "20";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            W7.setEnabled(false);
                            W7.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    W7.setEnabled(false);
                    W7.addActionListener(actionListenerW7);
                    Panel.add(W7);
                    JButton TH7 = new JButton("04pm-05pm");
                    TH7.setBounds(760, 250, 100, 80);
                    ActionListener actionListenerTH7 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "27";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            TH7.setEnabled(false);
                            TH7.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    TH7.setEnabled(false);
                    TH7.addActionListener(actionListenerTH7);
                    Panel.add(TH7);
                    JButton F7 = new JButton("04pm-05pm");
                    F7.setBounds(760, 330, 100, 80);
                    ActionListener actionListenerF7 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "34";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            F7.setEnabled(false);
                            F7.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    F7.setEnabled(false);
                    F7.addActionListener(actionListenerF7);
                    Panel.add(F7);
                    JButton S7 = new JButton("04pm-05pm");
                    S7.setBounds(760, 410, 100, 80);
                    ActionListener actionListenerS7 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "41";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            S7.setEnabled(false);
                            S7.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    S7.setEnabled(false);
                    S7.addActionListener(actionListenerS7);
                    Panel.add(S7);
                    // col-8
                    JButton M8 = new JButton("05pm-06pm");
                    M8.setBounds(870, 10, 100, 80);
                    ActionListener actionListenerM8 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "7";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            M8.setEnabled(false);
                            M8.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    M8.setEnabled(false);
                    M8.addActionListener(actionListenerM8);
                    Panel.add(M8);
                    JButton T8 = new JButton("05pm-06pm");
                    T8.setBounds(870, 90, 100, 80);
                    ActionListener actionListenerT8 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "14";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            T8.setEnabled(false);
                            T8.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    T8.setEnabled(false);
                    T8.addActionListener(actionListenerT8);
                    Panel.add(T8);
                    JButton W8 = new JButton("05pm-06pm");
                    W8.setBounds(870, 170, 100, 80);
                    ActionListener actionListenerW8 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "21";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            W8.setEnabled(false);
                            W8.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    W8.setEnabled(false);
                    W8.addActionListener(actionListenerW8);
                    Panel.add(W8);
                    JButton TH8 = new JButton("05pm-06pm");
                    TH8.setBounds(870, 250, 100, 80);
                    ActionListener actionListenerTH8 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "28";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            TH8.setEnabled(false);
                            TH8.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    TH8.setEnabled(false);
                    TH8.addActionListener(actionListenerTH8);
                    Panel.add(TH8);
                    JButton F8 = new JButton("05pm-06pm");
                    F8.setBounds(870, 330, 100, 80);
                    ActionListener actionListenerF8 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "35";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            F8.setEnabled(false);
                            F8.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    F8.setEnabled(false);
                    F8.addActionListener(actionListenerF8);
                    Panel.add(F8);

                    JButton S8 = new JButton("05pm-06pm");
                    S8.setBounds(870, 410, 100, 80);
                    ActionListener actionListenerS8 = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            time = "42";
                            sch = "Service is Finished!!";
                            JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                            S8.setEnabled(false);
                            S8.setBackground(null);
                            deleteRow(time);
                        };
                    };
                    S8.setEnabled(false);
                    S8.addActionListener(actionListenerS8);
                    Panel.add(S8);

                    // marking booked slots
                    for (int i : slots_over) {
                        if (i == 1) {
                            M1.setBackground(Color.decode("#EE3300"));

                        } else if (i == 2) {
                            M2.setBackground(Color.decode("#EE3300"));

                        } else if (i == 3) {
                            M3.setBackground(Color.decode("#EE3300"));

                        } else if (i == 4) {
                            M4.setBackground(Color.decode("#EE3300"));

                        } else if (i == 5) {
                            M6.setBackground(Color.decode("#EE3300"));

                        } else if (i == 6) {
                            M7.setBackground(Color.decode("#EE3300"));

                        } else if (i == 7) {
                            M8.setBackground(Color.decode("#EE3300"));

                        } else if (i == 8) {
                            T1.setBackground(Color.decode("#EE3300"));

                        } else if (i == 9) {
                            T2.setBackground(Color.decode("#EE3300"));

                        } else if (i == 10) {
                            T3.setBackground(Color.decode("#EE3300"));

                        } else if (i == 11) {
                            T4.setBackground(Color.decode("#EE3300"));

                        } else if (i == 12) {
                            T6.setBackground(Color.decode("#EE3300"));

                        } else if (i == 13) {
                            T7.setBackground(Color.decode("#EE3300"));

                        } else if (i == 14) {
                            T8.setBackground(Color.decode("#EE3300"));

                        } else if (i == 15) {
                            W1.setBackground(Color.decode("#EE3300"));

                        } else if (i == 16) {
                            W2.setBackground(Color.decode("#EE3300"));

                        } else if (i == 17) {
                            W3.setBackground(Color.decode("#EE3300"));

                        } else if (i == 18) {
                            W4.setBackground(Color.decode("#EE3300"));

                        } else if (i == 19) {
                            W6.setBackground(Color.decode("#EE3300"));

                        } else if (i == 20) {
                            W7.setBackground(Color.decode("#EE3300"));

                        } else if (i == 21) {
                            W8.setBackground(Color.decode("#EE3300"));

                        } else if (i == 22) {
                            TH1.setBackground(Color.decode("#EE3300"));

                        } else if (i == 23) {
                            TH2.setBackground(Color.decode("#EE3300"));

                        } else if (i == 24) {
                            TH3.setBackground(Color.decode("#EE3300"));

                        } else if (i == 25) {
                            TH4.setBackground(Color.decode("#EE3300"));

                        } else if (i == 26) {
                            TH6.setBackground(Color.decode("#EE3300"));

                        } else if (i == 27) {
                            TH7.setBackground(Color.decode("#EE3300"));

                        } else if (i == 28) {
                            TH8.setBackground(Color.decode("#EE3300"));

                        } else if (i == 29) {
                            F1.setBackground(Color.decode("#EE3300"));

                        } else if (i == 30) {
                            F2.setBackground(Color.decode("#EE3300"));

                        } else if (i == 31) {
                            F3.setBackground(Color.decode("#EE3300"));

                        } else if (i == 32) {
                            F4.setBackground(Color.decode("#EE3300"));

                        } else if (i == 33) {
                            F6.setBackground(Color.decode("#EE3300"));

                        } else if (i == 34) {
                            F7.setBackground(Color.decode("#EE3300"));

                        } else if (i == 35) {
                            F8.setBackground(Color.decode("#EE3300"));

                        } else if (i == 36) {
                            S1.setBackground(Color.decode("#EE3300"));

                        } else if (i == 37) {
                            S2.setBackground(Color.decode("#EE3300"));

                        } else if (i == 38) {
                            S3.setBackground(Color.decode("#EE3300"));

                        } else if (i == 39) {
                            S4.setBackground(Color.decode("#EE3300"));

                        } else if (i == 40) {
                            S6.setBackground(Color.decode("#EE3300"));

                        } else if (i == 41) {
                            S7.setBackground(Color.decode("#EE3300"));

                        } else if (i == 42) {
                            S8.setBackground(Color.decode("#EE3300"));

                        }
                        //

                        //

                        //
                        Panel.repaint();
                        frame.repaint();
                        frame.revalidate();
                        //

                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Failed to connect Database!", "VNRVJIET",
                            JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        frame.getContentPane().add(slot_img);

        Need.setBounds(470, 300, 150, 50);
        Need.setFont(new Font("Times New Roman", Font.BOLD, 20));
        Need.setBackground(new Color(61, 149, 207));
        Need.setForeground(Color.BLACK);
        Need.setFocusPainted(false);
        Need.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        ActionListener actionListenerNeed = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(Need);
                frame.remove(Doctor);
                frame.remove(QuestionL);
                frame.remove(slot_img);

                Panel.removeAll();
                frame.repaint();
                frame.revalidate();

                JLabel Name_P = new JLabel(
                        "<html><h2 style='color:grey;'>Name</h2></html>");
                Name_P.setBounds(120, 50, 100, 30);
                Name_P.setForeground(new Color(0, 0, 0));
                Name_P.setFont(new Font("Comic Sans MS", Font.ROMAN_BASELINE, 20));
                Panel.add(Name_P);
                JTextField Name_PtextField = new JTextField(null);
                Name_PtextField.setBounds(200, 50, 200, 30);
                Panel.add(Name_PtextField);

                JLabel Phone_P = new JLabel(
                        "<html><h2 style='color:grey;'>Phone</h2></html>");
                Phone_P.setBounds(120, 100, 100, 30);
                Phone_P.setForeground(new Color(0, 0, 0));
                Phone_P.setFont(new Font("Comic Sans MS", Font.ROMAN_BASELINE, 20));
                Panel.add(Phone_P);
                JTextField Phone_PtextField = new JTextField(null);
                Phone_PtextField.setBounds(200, 100, 200, 30);
                Panel.add(Phone_PtextField);

                JLabel Gender_P = new JLabel(
                        "<html><h2 style='color:grey;'>Gender</h2></html>");
                Gender_P.setBounds(105, 150, 100, 30);
                Gender_P.setForeground(new Color(0, 0, 0));
                Gender_P.setFont(new Font("Comic Sans MS", Font.ROMAN_BASELINE, 20));
                Panel.add(Gender_P);

                String[] options = { "Male", "Female", "Other" };
                JComboBox<String> comboBoxOption = new JComboBox<>(options);
                comboBoxOption.setBounds(200, 150, 200, 30);
                Panel.add(comboBoxOption);

                JLabel Age_P = new JLabel(
                        "<html><h2 style='color:grey;'>Age</h2></html>");
                Age_P.setBounds(135, 200, 100, 30);
                Age_P.setForeground(new Color(0, 0, 0));
                Age_P.setFont(new Font("Comic Sans MS", Font.ROMAN_BASELINE, 20));
                Panel.add(Age_P);
                JTextField Age_PtextField = new JTextField(null);
                Age_PtextField.setBounds(200, 200, 200, 30);
                Panel.add(Age_PtextField);

                JLabel Mail_P = new JLabel(
                        "<html><h2 style='color:grey;'>Email-ID</h2></html>");
                Mail_P.setBounds(90, 250, 100, 30);
                Mail_P.setForeground(new Color(0, 0, 0));
                Mail_P.setFont(new Font("Comic Sans MS", Font.ROMAN_BASELINE, 20));
                Panel.add(Mail_P);
                JTextField Mail_PtextField = new JTextField(null);
                Mail_PtextField.setBounds(200, 250, 200, 30);
                Panel.add(Mail_PtextField);

                JLabel Address_P = new JLabel(
                        "<html><h2 style='color:grey;'>Address</h2></html>");
                Address_P.setBounds(90, 300, 100, 30);
                Address_P.setForeground(new Color(0, 0, 0));
                Address_P.setFont(new Font("Comic Sans MS", Font.ROMAN_BASELINE, 20));
                Panel.add(Address_P);
                JTextField Address_PtextField = new JTextField(null);
                Address_PtextField.setBounds(200, 300, 200, 30);
                Panel.add(Address_PtextField);

                // BookNow button
                BookNow.setBounds(200, 350, 200, 50);
                BookNow.setFont(new Font("Times New Roman", Font.BOLD, 20));
                BookNow.setBackground(Color.decode("#FF991D"));
                BookNow.setForeground(Color.WHITE);
                BookNow.setFocusPainted(false);
                BookNow.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                //
                GoWalkIn.setBounds(200, 430, 200, 50);
                GoWalkIn.setFont(new Font("Times New Roman", Font.BOLD, 20));
                GoWalkIn.setBackground(Color.decode("#98991D"));
                GoWalkIn.setForeground(Color.WHITE);
                GoWalkIn.setFocusPainted(false);
                GoWalkIn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                //

                ActionListener actionListenerBookNow = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // verify data
                        String name = Name_PtextField.getText();
                        String ph = Phone_PtextField.getText();
                        String gen = (String) comboBoxOption.getSelectedItem();
                        String age = Age_PtextField.getText();
                        String mail = Mail_PtextField.getText();
                        String address = Address_PtextField.getText();
                        if (name.equals("") || ph.equals("") || gen.equals("") || age.equals("")
                                || mail.equals("") || address.equals("")) {
                            JOptionPane.showMessageDialog(null, "Invalid data Entered", "VNRVJIET",
                                    JOptionPane.ERROR_MESSAGE);
                        } else if (ph.length() != 10) {
                            JOptionPane.showMessageDialog(null, "Invalid Mobile Number Entered",
                                    "VNRVJIET",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            //
                            List<Integer> slots_over = new ArrayList<>();
                            try {
                                Class.forName("org.sqlite.JDBC");

                                Connection con = dbConnect();
                                Statement stmt = con.createStatement();
                                String Q = "select slot_id from user_data";
                                ResultSet rs = stmt.executeQuery(Q);

                                while (rs.next()) {
                                    int id = rs.getInt("slot_id");
                                    slots_over.add(id);
                                }

                                stmt.close();
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, "Failed to connect Database!", "VNRVJIET",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            //
                            frame.remove(Need);
                            frame.remove(Doctor);
                            frame.remove(QuestionL);
                            frame.remove(slot_img);

                            Panel.removeAll();
                            frame.repaint();
                            frame.revalidate();
                            Panel.setBounds(250, 150, 980, 550);

                            int x = 10;
                            // col-0
                            JButton M = new JButton("MON");
                            M.setBounds(x, 10, 80, 80);
                            M.setBackground(new Color(52, 2, 29));
                            M.setForeground(Color.BLACK);
                            M.setEnabled(false);
                            Panel.add(M);

                            JButton T = new JButton("TUE");
                            T.setBounds(x, 90, 80, 80);
                            T.setBackground(new Color(52, 2, 29));
                            T.setForeground(Color.BLACK);
                            T.setEnabled(false);
                            Panel.add(T);
                            JButton W = new JButton("WED");
                            W.setBounds(x, 170, 80, 80);
                            W.setBackground(new Color(52, 2, 29));
                            W.setForeground(Color.BLACK);
                            W.setEnabled(false);
                            Panel.add(W);
                            JButton TH = new JButton("THU");
                            TH.setBounds(x, 250, 80, 80);
                            TH.setBackground(new Color(52, 2, 29));
                            TH.setForeground(Color.BLACK);
                            TH.setEnabled(false);
                            Panel.add(TH);
                            JButton F = new JButton("FRI");
                            F.setBounds(x, 330, 80, 80);
                            F.setBackground(new Color(52, 2, 29));
                            F.setForeground(Color.BLACK);
                            F.setEnabled(false);
                            Panel.add(F);
                            JButton S = new JButton("SAT");
                            S.setBounds(x, 410, 80, 80);
                            S.setBackground(new Color(52, 2, 29));
                            S.setForeground(Color.BLACK);
                            S.setEnabled(false);
                            Panel.add(S);

                            // col1
                            JButton M1 = new JButton("10am-11am");
                            M1.setBounds(100, 10, 100, 80);
                            ActionListener actionListenerM1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "1";
                                    sch = "10am-11am,Monday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            M1.addActionListener(actionListenerM1);
                            Panel.add(M1);
                            JButton T1 = new JButton("10am-11am");
                            T1.setBounds(100, 90, 100, 80);
                            ActionListener actionListenerT1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "8";
                                    sch = "10am-11am,Tueday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            T1.addActionListener(actionListenerT1);
                            Panel.add(T1);
                            JButton W1 = new JButton("10am-11am");
                            W1.setBounds(100, 170, 100, 80);
                            ActionListener actionListenerW1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "15";
                                    sch = "10am-11am,Wednesday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            W1.addActionListener(actionListenerW1);
                            Panel.add(W1);
                            JButton TH1 = new JButton("10am-11am");
                            TH1.setBounds(100, 250, 100, 80);
                            ActionListener actionListenerTH1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "22";
                                    sch = "10am-11am,Thursday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            TH1.addActionListener(actionListenerTH1);
                            Panel.add(TH1);
                            JButton F1 = new JButton("10am-11am");
                            F1.setBounds(100, 330, 100, 80);
                            ActionListener actionListenerF1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "29";
                                    sch = "10am-11am,Friday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            F1.addActionListener(actionListenerF1);
                            Panel.add(F1);
                            JButton S1 = new JButton("10am-11am");
                            S1.setBounds(100, 410, 100, 80);
                            ActionListener actionListenerS1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "36";
                                    sch = "10am-11am,Saturday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            S1.addActionListener(actionListenerS1);
                            Panel.add(S1);

                            // col-2
                            JButton M2 = new JButton("11am-12pm");
                            M2.setBounds(210, 10, 100, 80);
                            ActionListener actionListenerM2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "2";
                                    sch = "11am-12pm,,Monday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            M2.addActionListener(actionListenerM2);
                            Panel.add(M2);
                            JButton T2 = new JButton("11am-12pm");
                            T2.setBounds(210, 90, 100, 80);
                            ActionListener actionListenerT2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "9";
                                    sch = "11am-12pm,Tuesday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            T2.addActionListener(actionListenerT2);
                            Panel.add(T2);
                            JButton W2 = new JButton("11am-12pm");
                            W2.setBounds(210, 170, 100, 80);
                            ActionListener actionListenerW2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "16";
                                    sch = "11am-12pm,Wednesday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            W2.addActionListener(actionListenerW2);
                            Panel.add(W2);
                            JButton TH2 = new JButton("11am-12pm");
                            TH2.setBounds(210, 250, 100, 80);
                            ActionListener actionListenerTH2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "23";
                                    sch = "11am-12pm,Thursday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            TH2.addActionListener(actionListenerTH2);
                            Panel.add(TH2);
                            JButton F2 = new JButton("11am-12pm");
                            F2.setBounds(210, 330, 100, 80);
                            ActionListener actionListenerF2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "30";
                                    sch = "11am-12pm,Friday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            F2.addActionListener(actionListenerF2);
                            Panel.add(F2);
                            JButton S2 = new JButton("11am-12pm");
                            S2.setBounds(210, 410, 100, 80);
                            ActionListener actionListenerS2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "37";
                                    sch = "11am-12pm,Saturday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            S2.addActionListener(actionListenerS2);
                            Panel.add(S2);
                            // col-3
                            JButton M3 = new JButton("12pm-01pm");
                            M3.setBounds(320, 10, 100, 80);
                            ActionListener actionListenerM3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "3";
                                    sch = "12pm-01pm,Monday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            M3.addActionListener(actionListenerM3);
                            Panel.add(M3);
                            JButton T3 = new JButton("12pm-01pm");
                            T3.setBounds(320, 90, 100, 80);
                            ActionListener actionListenerT3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "10";
                                    sch = "12pm-01pm,Tueday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            T3.addActionListener(actionListenerT3);
                            Panel.add(T3);
                            JButton W3 = new JButton("12pm-01pm");
                            W3.setBounds(320, 170, 100, 80);
                            ActionListener actionListenerW3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "17";
                                    sch = "12pm-01pm,Wednesday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            W3.addActionListener(actionListenerW3);
                            Panel.add(W3);
                            JButton TH3 = new JButton("12pm-01pm");
                            TH3.setBounds(320, 250, 100, 80);
                            ActionListener actionListenerTH3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "24";
                                    sch = "12pm-01pm,Thursday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            TH3.addActionListener(actionListenerTH3);
                            Panel.add(TH3);
                            JButton F3 = new JButton("12pm-01pm");
                            F3.setBounds(320, 330, 100, 80);
                            ActionListener actionListenerF3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "31";
                                    sch = "12pm-01pm,Friday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            F3.addActionListener(actionListenerF3);
                            Panel.add(F3);
                            JButton S3 = new JButton("12pm-01pm");
                            S3.setBounds(320, 410, 100, 80);
                            ActionListener actionListenerS3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "38";
                                    sch = "12pm-01pm,Saturday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            S3.addActionListener(actionListenerS3);
                            Panel.add(S3);
                            // col-4
                            JButton M4 = new JButton("01pm-02pm");
                            M4.setBounds(430, 10, 100, 80);
                            ActionListener actionListenerM4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "4";
                                    sch = "01pm-02pm,Monday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            M4.addActionListener(actionListenerM4);
                            Panel.add(M4);
                            JButton T4 = new JButton("01pm-02pm");
                            T4.setBounds(430, 90, 100, 80);
                            ActionListener actionListenerT4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "11";
                                    sch = "01pm-02pm,Tueday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            T4.addActionListener(actionListenerT4);
                            Panel.add(T4);
                            JButton W4 = new JButton("01pm-02pm");
                            W4.setBounds(430, 170, 100, 80);
                            ActionListener actionListenerW4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "18";
                                    sch = "01pm-02pm,Wednesday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            W4.addActionListener(actionListenerW4);
                            Panel.add(W4);
                            JButton TH4 = new JButton("01pm-02pm");
                            TH4.setBounds(430, 250, 100, 80);
                            ActionListener actionListenerTH4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "25";
                                    sch = "01pm-02pm,Thursday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            TH4.addActionListener(actionListenerTH4);
                            Panel.add(TH4);
                            JButton F4 = new JButton("01pm-02pm");
                            F4.setBounds(430, 330, 100, 80);
                            ActionListener actionListenerF4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "32";
                                    sch = "01pm-02pm,Friday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            F4.addActionListener(actionListenerF4);
                            Panel.add(F4);
                            JButton S4 = new JButton("01pm-02pm");
                            S4.setBounds(430, 410, 100, 80);
                            ActionListener actionListenerS4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "39";
                                    sch = "01pm-02pm,Saturday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            S4.addActionListener(actionListenerS4);
                            Panel.add(S4);
                            // col-5
                            JButton M5 = new JButton("Break");
                            M5.setBounds(540, 10, 100, 80);
                            M5.setEnabled(false);
                            Panel.add(M5);
                            JButton T5 = new JButton("Break");
                            T5.setBounds(540, 90, 100, 80);
                            T5.setEnabled(false);
                            Panel.add(T5);
                            JButton W5 = new JButton("Break");
                            W5.setBounds(540, 170, 100, 80);
                            W5.setEnabled(false);
                            Panel.add(W5);
                            JButton TH5 = new JButton("Break");
                            TH5.setBounds(540, 250, 100, 80);
                            TH5.setEnabled(false);
                            Panel.add(TH5);
                            JButton F5 = new JButton("Break");
                            F5.setBounds(540, 330, 100, 80);
                            F5.setEnabled(false);
                            Panel.add(F5);
                            JButton S5 = new JButton("Break");
                            S5.setBounds(540, 410, 100, 80);
                            S5.setEnabled(false);
                            Panel.add(S5);
                            // col-6
                            JButton M6 = new JButton("03pm-04pm");
                            M6.setBounds(650, 10, 100, 80);
                            ActionListener actionListenerM6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "5";
                                    sch = "03pm-04pm,Monday";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            M6.addActionListener(actionListenerM6);
                            Panel.add(M6);
                            JButton T6 = new JButton("03pm-04pm");
                            T6.setBounds(650, 90, 100, 80);
                            ActionListener actionListenerT6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "12";
                                    sch = "03pm-04pm,Tueday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            T6.addActionListener(actionListenerT6);
                            Panel.add(T6);
                            JButton W6 = new JButton("03pm-04pm");
                            W6.setBounds(650, 170, 100, 80);
                            ActionListener actionListenerW6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "19";
                                    sch = "03pm-04pm,Wednesday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            W6.addActionListener(actionListenerW6);
                            Panel.add(W6);
                            JButton TH6 = new JButton("03pm-04pm");
                            TH6.setBounds(650, 250, 100, 80);
                            ActionListener actionListenerTH6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "26";
                                    sch = "03pm-04pm,Thursday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            TH6.addActionListener(actionListenerTH6);
                            Panel.add(TH6);
                            JButton F6 = new JButton("03pm-04pm");
                            F6.setBounds(650, 330, 100, 80);
                            ActionListener actionListenerF6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "33";
                                    sch = "03pm-04pm,Friday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            F6.addActionListener(actionListenerF6);
                            Panel.add(F6);
                            JButton S6 = new JButton("03pm-04pm");
                            S6.setBounds(650, 410, 100, 80);
                            ActionListener actionListenerS6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "40";
                                    sch = "03pm-04pm,Saturday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            S6.addActionListener(actionListenerS6);
                            Panel.add(S6);
                            // col-7
                            JButton M7 = new JButton("04pm-05pm");
                            M7.setBounds(760, 10, 100, 80);
                            ActionListener actionListenerM7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "6";
                                    sch = "04pm-05pm,Monday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            M7.addActionListener(actionListenerM7);
                            Panel.add(M7);
                            JButton T7 = new JButton("04pm-05pm");
                            T7.setBounds(760, 90, 100, 80);
                            ActionListener actionListenerT7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "13";
                                    sch = "04pm-05pm,Tueday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            T7.addActionListener(actionListenerT7);
                            Panel.add(T7);
                            JButton W7 = new JButton("04pm-05pm");
                            W7.setBounds(760, 170, 100, 80);
                            ActionListener actionListenerW7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "20";
                                    sch = "04pm-05pm,Wednesday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            W7.addActionListener(actionListenerW7);
                            Panel.add(W7);
                            JButton TH7 = new JButton("04pm-05pm");
                            TH7.setBounds(760, 250, 100, 80);
                            ActionListener actionListenerTH7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "27";
                                    sch = "04pm-05pm,Thursday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            TH7.addActionListener(actionListenerTH7);
                            Panel.add(TH7);
                            JButton F7 = new JButton("04pm-05pm");
                            F7.setBounds(760, 330, 100, 80);
                            ActionListener actionListenerF7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "34";
                                    sch = "04pm-05pm,Friday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            F7.addActionListener(actionListenerF7);
                            Panel.add(F7);
                            JButton S7 = new JButton("04pm-05pm");
                            S7.setBounds(760, 410, 100, 80);
                            ActionListener actionListenerS7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "41";
                                    sch = "04pm-05pm,Saturday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            S7.addActionListener(actionListenerS7);
                            Panel.add(S7);
                            // col-8
                            JButton M8 = new JButton("05pm-06pm");
                            M8.setBounds(870, 10, 100, 80);
                            ActionListener actionListenerM8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "7";
                                    sch = ",Monday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            M8.addActionListener(actionListenerM8);
                            Panel.add(M8);
                            JButton T8 = new JButton("05pm-06pm");
                            T8.setBounds(870, 90, 100, 80);
                            ActionListener actionListenerT8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "14";
                                    sch = "05pm-06pm,Tueday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            T8.addActionListener(actionListenerT8);
                            Panel.add(T8);
                            JButton W8 = new JButton("05pm-06pm");
                            W8.setBounds(870, 170, 100, 80);
                            ActionListener actionListenerW8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "21";
                                    sch = "05pm-06pm,Wednesday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            W8.addActionListener(actionListenerW8);
                            Panel.add(W8);
                            JButton TH8 = new JButton("05pm-06pm");
                            TH8.setBounds(870, 250, 100, 80);
                            ActionListener actionListenerTH8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "28";
                                    sch = "05pm-06pm,Thursday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            TH8.addActionListener(actionListenerTH8);
                            Panel.add(TH8);
                            JButton F8 = new JButton("05pm-06pm");
                            F8.setBounds(870, 330, 100, 80);
                            ActionListener actionListenerF8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "35";
                                    sch = "05pm-06pm,Friday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            F8.addActionListener(actionListenerF8);
                            Panel.add(F8);
                            JButton S8 = new JButton("05pm-06pm");
                            S8.setBounds(870, 410, 100, 80);
                            ActionListener actionListenerS8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "42";
                                    sch = "05pm-06pm,Saturday";
                                    JOptionPane.showMessageDialog(null, sch, null, JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            S8.addActionListener(actionListenerS8);
                            Panel.add(S8);

                            JButton Done_app = new JButton("Confirm!");
                            Done_app.setBounds(430, 500, 150, 40);
                            Done_app.setFont(new Font("Times New Roman", Font.BOLD, 20));
                            Done_app.setBackground(new Color(61, 149, 207));
                            Done_app.setForeground(Color.BLACK);
                            Done_app.setFocusPainted(false);
                            ActionListener actionListenerDone_app = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    JOptionPane.showMessageDialog(null,
                                            "Your Appointment is scheduled according to your request! ", null,
                                            JOptionPane.INFORMATION_MESSAGE);

                                    try {
                                        Class.forName("org.sqlite.JDBC");
                                        LocalTime localIST = LocalTime.now();
                                        int localtime = localIST.getHour();

                                        int day_no = 0;
                                        int x = Integer.valueOf(time);
                                        if (x >= 1 && x <= 7)
                                            day_no = 1;
                                        else if (x >= 8 && x <= 14)
                                            day_no = 2;
                                        else if (x >= 15 && x <= 21)
                                            day_no = 3;
                                        else if (x >= 22 && x <= 28)
                                            day_no = 4;
                                        else if (x >= 29 && x <= 35)
                                            day_no = 5;
                                        else if (x >= 36 && x <= 42)
                                            day_no = 6;
                                        int y = Integer.valueOf(time);
                                        int time1 = 9;
                                        if (y == 1 || y == 8 || y == 15 || y == 22 || y == 29 || y == 36) {
                                            time1 = (10);
                                        } else if (y == 2 || y == 9 || y == 16 || y == 23 || y == 30 || y == 37) {
                                            time1 = (11);
                                        } else if (y == 3 || y == 10 || y == 17 || y == 24 || y == 31 || y == 38) {
                                            time1 = (12);
                                        } else if (y == 4 || y == 11 || y == 18 || y == 25 || y == 32 || y == 39) {
                                            time1 = (13);
                                        } else if (y == 5 || y == 12 || y == 19 || y == 26 || y == 33 || y == 40) {
                                            time1 = (15);
                                        } else if (y == 6 || y == 13 || y == 20 || y == 27 || y == 34 || y == 41) {
                                            time1 = (16);
                                        } else if (y == 7 || y == 14 || y == 21 || y == 28 || y == 35 || y == 42) {
                                            time1 = (17);
                                        }

                                        Connection con = dbConnect();
                                        Statement stmt = con.createStatement();
                                        String Q = "INSERT INTO user_data (name, phone, gender, age, email_id, address,schedule,slot_id,time_stamp,day_no,cur_time) VALUES ('"
                                                + name + "', '" + ph + "', '" + gen + "', " + age + ", '" + mail
                                                + "', '" + address + "','" + sch + "','" + time + "'," + localtime + ","
                                                + day_no + "," + time1 + ")";

                                        stmt.execute(Q);
                                        stmt.close();
                                        // obj = null;
                                        frame.dispose();
                                        new Main();

                                    } catch (Exception e1) {
                                        JOptionPane.showMessageDialog(null, "Failed to connect Database!", "VNRVJIET",
                                                JOptionPane.ERROR_MESSAGE);

                                    }

                                };
                            };
                            Done_app.addActionListener(actionListenerDone_app);
                            Panel.add(Done_app);

                            // marking booked slots
                            for (int i : slots_over) {
                                if (i == 1) {
                                    M1.setBackground(Color.decode("#EE3300"));
                                    M1.setEnabled(false);
                                } else if (i == 2) {
                                    M2.setBackground(Color.decode("#EE3300"));
                                    M2.setEnabled(false);
                                } else if (i == 3) {
                                    M3.setBackground(Color.decode("#EE3300"));
                                    M3.setEnabled(false);
                                } else if (i == 4) {
                                    M4.setBackground(Color.decode("#EE3300"));
                                    M4.setEnabled(false);
                                } else if (i == 5) {
                                    M6.setBackground(Color.decode("#EE3300"));
                                    M6.setEnabled(false);
                                } else if (i == 6) {
                                    M7.setBackground(Color.decode("#EE3300"));
                                    M7.setEnabled(false);
                                } else if (i == 7) {
                                    M8.setBackground(Color.decode("#EE3300"));
                                    M8.setEnabled(false);
                                } else if (i == 8) {
                                    T1.setBackground(Color.decode("#EE3300"));
                                    T1.setEnabled(false);
                                } else if (i == 9) {
                                    T2.setBackground(Color.decode("#EE3300"));
                                    T2.setEnabled(false);
                                } else if (i == 10) {
                                    T3.setBackground(Color.decode("#EE3300"));
                                    T3.setEnabled(false);
                                } else if (i == 11) {
                                    T4.setBackground(Color.decode("#EE3300"));
                                    T4.setEnabled(false);
                                } else if (i == 12) {
                                    T6.setBackground(Color.decode("#EE3300"));
                                    T6.setEnabled(false);
                                } else if (i == 13) {
                                    T7.setBackground(Color.decode("#EE3300"));
                                    T7.setEnabled(false);
                                } else if (i == 14) {
                                    T8.setBackground(Color.decode("#EE3300"));
                                    T8.setEnabled(false);
                                } else if (i == 15) {
                                    W1.setBackground(Color.decode("#EE3300"));
                                    W1.setEnabled(false);
                                } else if (i == 16) {
                                    W2.setBackground(Color.decode("#EE3300"));
                                    W2.setEnabled(false);
                                } else if (i == 17) {
                                    W3.setBackground(Color.decode("#EE3300"));
                                    W3.setEnabled(false);
                                } else if (i == 18) {
                                    W4.setBackground(Color.decode("#EE3300"));
                                    W4.setEnabled(false);
                                } else if (i == 19) {
                                    W6.setBackground(Color.decode("#EE3300"));
                                    W6.setEnabled(false);
                                } else if (i == 20) {
                                    W7.setBackground(Color.decode("#EE3300"));
                                    W7.setEnabled(false);
                                } else if (i == 21) {
                                    W8.setBackground(Color.decode("#EE3300"));
                                    W8.setEnabled(false);
                                } else if (i == 22) {
                                    TH1.setBackground(Color.decode("#EE3300"));
                                    TH1.setEnabled(false);
                                } else if (i == 23) {
                                    TH2.setBackground(Color.decode("#EE3300"));
                                    TH2.setEnabled(false);
                                } else if (i == 24) {
                                    TH3.setBackground(Color.decode("#EE3300"));
                                    TH3.setEnabled(false);
                                } else if (i == 25) {
                                    TH4.setBackground(Color.decode("#EE3300"));
                                    TH4.setEnabled(false);
                                } else if (i == 26) {
                                    TH6.setBackground(Color.decode("#EE3300"));
                                    TH6.setEnabled(false);
                                } else if (i == 27) {
                                    TH7.setBackground(Color.decode("#EE3300"));
                                    TH7.setEnabled(false);
                                } else if (i == 28) {
                                    TH8.setBackground(Color.decode("#EE3300"));
                                    TH8.setEnabled(false);
                                } else if (i == 29) {
                                    F1.setBackground(Color.decode("#EE3300"));
                                    F1.setEnabled(false);
                                } else if (i == 30) {
                                    F2.setBackground(Color.decode("#EE3300"));
                                    F2.setEnabled(false);
                                } else if (i == 31) {
                                    F3.setBackground(Color.decode("#EE3300"));
                                    F3.setEnabled(false);
                                } else if (i == 32) {
                                    F4.setBackground(Color.decode("#EE3300"));
                                    F4.setEnabled(false);
                                } else if (i == 33) {
                                    F6.setBackground(Color.decode("#EE3300"));
                                    F6.setEnabled(false);
                                } else if (i == 34) {
                                    F7.setBackground(Color.decode("#EE3300"));
                                    F7.setEnabled(false);
                                } else if (i == 35) {
                                    F8.setBackground(Color.decode("#EE3300"));
                                    F8.setEnabled(false);
                                } else if (i == 36) {
                                    S1.setBackground(Color.decode("#EE3300"));
                                    S1.setEnabled(false);
                                } else if (i == 37) {
                                    S2.setBackground(Color.decode("#EE3300"));
                                    S2.setEnabled(false);
                                } else if (i == 38) {
                                    S3.setBackground(Color.decode("#EE3300"));
                                    S3.setEnabled(false);
                                } else if (i == 39) {
                                    S4.setBackground(Color.decode("#EE3300"));
                                    S4.setEnabled(false);
                                } else if (i == 40) {
                                    S6.setBackground(Color.decode("#EE3300"));
                                    S6.setEnabled(false);
                                } else if (i == 41) {
                                    S7.setBackground(Color.decode("#EE3300"));
                                    S7.setEnabled(false);
                                } else if (i == 42) {
                                    S8.setBackground(Color.decode("#EE3300"));
                                    S8.setEnabled(false);
                                }
                            }
                            //
                            JButton Back = new JButton("<html><h2>Back</h2></html>");
                            Back.setBounds(800, 500, 100, 40);
                            Back.setBackground(Color.decode("#128800"));
                            Back.setForeground(Color.WHITE);
                            ActionListener actionListenerBack = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    frame.dispose();
                                    obj = new Main();
                                    // JOptionPane.showMessageDialog(null, "", "VNRVJIET",
                                    // JOptionPane.OK_CANCEL_OPTION);
                                };
                            };
                            Back.addActionListener(actionListenerBack);
                            Panel.add(Back);
                            //

                            Name_PtextField.setText("");
                            Phone_PtextField.setText("");
                            comboBoxOption.setSelectedItem("");
                            Age_PtextField.setText("");
                            Mail_PtextField.setText("");
                            Address_PtextField.setText("");
                            //
                            Panel.repaint();
                            frame.repaint();
                            frame.revalidate();
                        }
                    }
                };
                BookNow.addActionListener(actionListenerBookNow);
                Panel.add(BookNow);
                ActionListener actionListenerGoWalkIn = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Panel.remove(BookNow);
                        Panel.remove(Address_PtextField);
                        Panel.remove(Address_P);
                        Panel.remove(Mail_P);
                        Panel.remove(Mail_PtextField);
                        Panel.remove(GoWalkIn);
                        Panel.repaint();
                        frame.repaint();
                        frame.revalidate();

                        Name_PtextField.setBounds(200, 100, 200, 30);
                        Name_P.setBounds(120, 100, 100, 30);
                        Phone_PtextField.setBounds(200, 150, 200, 30);
                        Phone_P.setBounds(120, 150, 100, 30);
                        Gender_P.setBounds(105, 200, 100, 30);
                        comboBoxOption.setBounds(200, 200, 200, 30);
                        Age_PtextField.setBounds(200, 250, 200, 30);
                        Age_P.setBounds(135, 250, 100, 30);

                        JButton Token = new JButton("Token");
                        Token.setBounds(200, 300, 200, 50);
                        Token.setFont(new Font("Times New Roman", Font.BOLD, 20));
                        Token.setBackground(Color.decode("#418989"));
                        Token.setForeground(Color.WHITE);
                        Token.setFocusPainted(false);
                        Token.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                        ActionListener actionListenerToken = new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                //

                                // verify data
                                String name = Name_PtextField.getText();
                                String ph = Phone_PtextField.getText();
                                String gen = (String) comboBoxOption.getSelectedItem();
                                String age = Age_PtextField.getText();
                                if (name.equals("") || ph.equals("") || gen.equals("") || age.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Invalid data Entered", "VNRVJIET",
                                            JOptionPane.ERROR_MESSAGE);
                                } else if (ph.length() != 10) {
                                    JOptionPane.showMessageDialog(null, "Invalid Mobile Number Entered",
                                            "VNRVJIET",
                                            JOptionPane.ERROR_MESSAGE);
                                } else {

                                    //
                                    try {
                                        Class.forName("org.sqlite.JDBC");

                                        Connection con = dbConnect();
                                        Statement stmt = con.createStatement();
                                        String Q = "insert into walk_in values('" + name + "','" + ph + "','" + gen
                                                + "'," + age + ")";
                                        stmt.execute(Q);
                                        stmt.close();
                                        Name_PtextField.setText("");
                                        Phone_PtextField.setText("");
                                        Age_PtextField.setText("");
                                        JOptionPane.showMessageDialog(null, "Go and Stand in the Queue!!", "VNRVJIET",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        // frame.dispose();
                                        // new Main();
                                    } catch (Exception e1) {
                                        JOptionPane.showMessageDialog(null, "Failed to connect Database!", "VNRVJIET",
                                                JOptionPane.ERROR_MESSAGE);
                                        System.out.println(e1.getMessage());
                                    }
                                }
                            }
                        };
                        Token.addActionListener(actionListenerToken);
                        Panel.add(Token);
                    }
                };
                GoWalkIn.addActionListener(actionListenerGoWalkIn);
                Panel.add(GoWalkIn);

                // landing logo start
                String[] imgs = { "1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg" };

                ImageIcon landinglogo = new ImageIcon(imgs[new Random().nextInt(imgs.length)]);
                Image ilandinglogo = landinglogo.getImage();
                Image resizedlandinglogo = ilandinglogo.getScaledInstance(420, 420, Image.SCALE_SMOOTH);
                ImageIcon relandinglogo = new ImageIcon(resizedlandinglogo);
                JLabel imgrelandinglogo = new JLabel(relandinglogo);
                imgrelandinglogo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                imgrelandinglogo.setBounds(500, 50, 420, 420);
                imgrelandinglogo.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        refresh = 0;
                        imgrelandinglogo.setToolTipText("Ѕίleͷҫe Pleāše");
                        super.mouseEntered(e);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        refresh = 0;
                        imgrelandinglogo.setToolTipText("");
                        super.mouseExited(e);
                    }
                });
                Panel.add(imgrelandinglogo);

                // landing logo over

                //
                Panel.setBounds(250, 75, 1000, 550);
                frame.repaint();
                frame.revalidate();
            }
        };
        Need.addActionListener(actionListenerNeed);
        frame.add(Need);
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        // doctor button
        Doctor.setBounds(710, 300, 150, 50);
        Doctor.setFont(new Font("Times New Roman", Font.BOLD, 20));
        Doctor.setBackground(new Color(61, 149, 207));
        Doctor.setForeground(Color.BLACK);
        Doctor.setFocusPainted(false);
        Doctor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        ActionListener actionListenerDoctor = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //
                //
                while (true) {
                    String psw = JOptionPane.showInputDialog(null,
                            "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Enter Password:</h></b></html>",
                            "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                    // int p = 0;
                    if (psw == null)
                        break;

                    if (psw.equals("1010")) {
                        JOptionPane.showMessageDialog(null,
                                "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Welcome Doctor!!</h></b></html>",
                                "VNRVJIET", JOptionPane.INFORMATION_MESSAGE);

                        //
                        //
                        //
                        //
                        //
                        List<Integer> slots_over = new ArrayList<>();
                        try {
                            Class.forName("org.sqlite.JDBC");

                            Connection con = dbConnect();
                            Statement stmt = con.createStatement();
                            String Q = "select slot_id from user_data";
                            ResultSet rs = stmt.executeQuery(Q);

                            while (rs.next()) {
                                int id = rs.getInt("slot_id");
                                slots_over.add(id);
                            }

                            stmt.close();

                            //
                            frame.remove(Need);
                            frame.remove(Doctor);
                            frame.remove(QuestionL);
                            frame.remove(slot_img);

                            Panel.removeAll();
                            frame.repaint();
                            frame.revalidate();
                            Panel.setBounds(250, 150, 980, 550);

                            int x = 10;
                            // col-0
                            JButton M = new JButton("MON");
                            M.setBounds(x, 10, 80, 80);
                            M.setBackground(new Color(52, 2, 29));
                            M.setForeground(Color.BLACK);
                            M.setEnabled(false);
                            Panel.add(M);

                            JButton T = new JButton("TUE");
                            T.setBounds(x, 90, 80, 80);
                            T.setBackground(new Color(52, 2, 29));
                            T.setForeground(Color.BLACK);
                            T.setEnabled(false);
                            Panel.add(T);
                            JButton W = new JButton("WED");
                            W.setBounds(x, 170, 80, 80);
                            W.setBackground(new Color(52, 2, 29));
                            W.setForeground(Color.BLACK);
                            W.setEnabled(false);
                            Panel.add(W);
                            JButton TH = new JButton("THU");
                            TH.setBounds(x, 250, 80, 80);
                            TH.setBackground(new Color(52, 2, 29));
                            TH.setForeground(Color.BLACK);
                            TH.setEnabled(false);
                            Panel.add(TH);
                            JButton F = new JButton("FRI");
                            F.setBounds(x, 330, 80, 80);
                            F.setBackground(new Color(52, 2, 29));
                            F.setForeground(Color.BLACK);
                            F.setEnabled(false);
                            Panel.add(F);
                            JButton S = new JButton("SAT");
                            S.setBounds(x, 410, 80, 80);
                            S.setBackground(new Color(52, 2, 29));
                            S.setForeground(Color.BLACK);
                            S.setEnabled(false);
                            Panel.add(S);

                            // col1
                            JButton M1 = new JButton("10am-11am");
                            M1.setBounds(100, 10, 100, 80);
                            ActionListener actionListenerM1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "1";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    M1.setEnabled(false);
                                    M1.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            M1.setEnabled(false);
                            M1.addActionListener(actionListenerM1);
                            Panel.add(M1);
                            JButton T1 = new JButton("10am-11am");
                            T1.setBounds(100, 90, 100, 80);
                            ActionListener actionListenerT1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "8";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    T1.setEnabled(false);
                                    T1.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            T1.setEnabled(false);
                            T1.addActionListener(actionListenerT1);
                            Panel.add(T1);
                            JButton W1 = new JButton("10am-11am");
                            W1.setBounds(100, 170, 100, 80);
                            ActionListener actionListenerW1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "15";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    W1.setEnabled(false);
                                    W1.setBackground(null);
                                    deleteRow(time);
                                };

                            };
                            W1.setEnabled(false);
                            W1.addActionListener(actionListenerW1);
                            Panel.add(W1);
                            JButton TH1 = new JButton("10am-11am");
                            TH1.setBounds(100, 250, 100, 80);
                            ActionListener actionListenerTH1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "22";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    TH1.setEnabled(false);
                                    TH1.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            TH1.setEnabled(false);
                            TH1.addActionListener(actionListenerTH1);
                            Panel.add(TH1);
                            JButton F1 = new JButton("10am-11am");
                            F1.setBounds(100, 330, 100, 80);
                            ActionListener actionListenerF1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "29";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    F1.setEnabled(false);
                                    F1.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            F1.setEnabled(false);
                            F1.addActionListener(actionListenerF1);
                            Panel.add(F1);
                            JButton S1 = new JButton("10am-11am");
                            S1.setBounds(100, 410, 100, 80);
                            ActionListener actionListenerS1 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "36";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    S1.setEnabled(false);
                                    S1.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            S1.setEnabled(false);
                            S1.addActionListener(actionListenerS1);
                            Panel.add(S1);

                            // col-2
                            JButton M2 = new JButton("11am-12pm");
                            M2.setBounds(210, 10, 100, 80);
                            ActionListener actionListenerM2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "2";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    M2.setEnabled(false);
                                    M2.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            M2.setEnabled(false);
                            M2.addActionListener(actionListenerM2);
                            Panel.add(M2);
                            JButton T2 = new JButton("11am-12pm");
                            T2.setBounds(210, 90, 100, 80);
                            ActionListener actionListenerT2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "9";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    T2.setEnabled(false);
                                    T2.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            T2.setEnabled(false);
                            T2.addActionListener(actionListenerT2);
                            Panel.add(T2);
                            JButton W2 = new JButton("11am-12pm");
                            W2.setBounds(210, 170, 100, 80);
                            ActionListener actionListenerW2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "16";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    W2.setEnabled(false);
                                    W2.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            W2.setEnabled(false);
                            W2.addActionListener(actionListenerW2);
                            Panel.add(W2);
                            JButton TH2 = new JButton("11am-12pm");
                            TH2.setBounds(210, 250, 100, 80);
                            ActionListener actionListenerTH2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "23";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    TH2.setEnabled(false);
                                    TH2.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            TH2.setEnabled(false);
                            TH2.addActionListener(actionListenerTH2);
                            Panel.add(TH2);
                            JButton F2 = new JButton("11am-12pm");
                            F2.setBounds(210, 330, 100, 80);
                            ActionListener actionListenerF2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "30";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    F2.setEnabled(false);
                                    F2.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            F2.setEnabled(false);
                            F2.addActionListener(actionListenerF2);
                            Panel.add(F2);
                            JButton S2 = new JButton("11am-12pm");
                            S2.setBounds(210, 410, 100, 80);
                            ActionListener actionListenerS2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "37";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    S2.setEnabled(false);
                                    S2.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            S2.setEnabled(false);
                            S2.addActionListener(actionListenerS2);
                            Panel.add(S2);
                            // col-3
                            JButton M3 = new JButton("12pm-01pm");
                            M3.setBounds(320, 10, 100, 80);
                            ActionListener actionListenerM3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "3";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    M3.setEnabled(false);
                                    M3.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            M3.setEnabled(false);
                            M3.addActionListener(actionListenerM3);
                            Panel.add(M3);
                            JButton T3 = new JButton("12pm-01pm");
                            T3.setBounds(320, 90, 100, 80);
                            ActionListener actionListenerT3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "10";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    T3.setEnabled(false);
                                    T3.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            T3.setEnabled(false);
                            T3.addActionListener(actionListenerT3);
                            Panel.add(T3);
                            JButton W3 = new JButton("12pm-01pm");
                            W3.setBounds(320, 170, 100, 80);
                            ActionListener actionListenerW3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "17";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    W3.setEnabled(false);
                                    W3.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            W3.setEnabled(false);
                            W3.addActionListener(actionListenerW3);
                            Panel.add(W3);
                            JButton TH3 = new JButton("12pm-01pm");
                            TH3.setBounds(320, 250, 100, 80);
                            ActionListener actionListenerTH3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "24";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    TH3.setEnabled(false);
                                    TH3.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            TH3.setEnabled(false);
                            TH3.addActionListener(actionListenerTH3);
                            Panel.add(TH3);
                            JButton F3 = new JButton("12pm-01pm");
                            F3.setBounds(320, 330, 100, 80);
                            ActionListener actionListenerF3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "31";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    F3.setEnabled(false);
                                    F3.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            F3.setEnabled(false);
                            F3.addActionListener(actionListenerF3);
                            Panel.add(F3);
                            JButton S3 = new JButton("12pm-01pm");
                            S3.setBounds(320, 410, 100, 80);
                            ActionListener actionListenerS3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "38";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    S3.setEnabled(false);
                                    S3.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            S3.setEnabled(false);
                            S3.addActionListener(actionListenerS3);
                            Panel.add(S3);
                            // col-4
                            JButton M4 = new JButton("01pm-02pm");
                            M4.setBounds(430, 10, 100, 80);
                            ActionListener actionListenerM4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "4";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    M4.setEnabled(false);
                                    M4.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            M4.setEnabled(false);
                            M4.addActionListener(actionListenerM4);
                            Panel.add(M4);
                            JButton T4 = new JButton("01pm-02pm");
                            T4.setBounds(430, 90, 100, 80);
                            ActionListener actionListenerT4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "11";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    T4.setEnabled(false);
                                    T4.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            T4.setEnabled(false);
                            T4.addActionListener(actionListenerT4);
                            Panel.add(T4);
                            JButton W4 = new JButton("01pm-02pm");
                            W4.setBounds(430, 170, 100, 80);
                            ActionListener actionListenerW4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "18";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    W4.setEnabled(false);
                                    W4.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            W4.setEnabled(false);
                            W4.addActionListener(actionListenerW4);
                            Panel.add(W4);
                            JButton TH4 = new JButton("01pm-02pm");
                            TH4.setBounds(430, 250, 100, 80);
                            ActionListener actionListenerTH4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "25";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    TH4.setEnabled(false);
                                    TH4.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            TH4.setEnabled(false);
                            TH4.addActionListener(actionListenerTH4);
                            Panel.add(TH4);
                            JButton F4 = new JButton("01pm-02pm");
                            F4.setBounds(430, 330, 100, 80);
                            ActionListener actionListenerF4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "32";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    F4.setEnabled(false);
                                    F4.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            F4.setEnabled(false);
                            F4.addActionListener(actionListenerF4);
                            Panel.add(F4);
                            JButton S4 = new JButton("01pm-02pm");
                            S4.setBounds(430, 410, 100, 80);
                            ActionListener actionListenerS4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "39";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    S4.setEnabled(false);
                                    S4.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            S4.setEnabled(false);
                            S4.addActionListener(actionListenerS4);
                            Panel.add(S4);
                            // col-5
                            JButton M5 = new JButton("Break");
                            M5.setBounds(540, 10, 100, 80);
                            M5.setEnabled(false);
                            Panel.add(M5);
                            JButton T5 = new JButton("Break");
                            T5.setBounds(540, 90, 100, 80);
                            T5.setEnabled(false);
                            Panel.add(T5);
                            JButton W5 = new JButton("Break");
                            W5.setBounds(540, 170, 100, 80);
                            W5.setEnabled(false);
                            Panel.add(W5);
                            JButton TH5 = new JButton("Break");
                            TH5.setBounds(540, 250, 100, 80);
                            TH5.setEnabled(false);
                            Panel.add(TH5);
                            JButton F5 = new JButton("Break");
                            F5.setBounds(540, 330, 100, 80);
                            F5.setEnabled(false);
                            Panel.add(F5);
                            JButton S5 = new JButton("Break");
                            S5.setBounds(540, 410, 100, 80);
                            S5.setEnabled(false);
                            Panel.add(S5);
                            // col-6
                            JButton M6 = new JButton("03pm-04pm");
                            M6.setBounds(650, 10, 100, 80);
                            ActionListener actionListenerM6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "5";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    M6.setEnabled(false);
                                    M6.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            M6.setEnabled(false);
                            M6.addActionListener(actionListenerM6);
                            Panel.add(M6);
                            JButton T6 = new JButton("03pm-04pm");
                            T6.setBounds(650, 90, 100, 80);
                            ActionListener actionListenerT6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "12";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    T6.setEnabled(false);
                                    T6.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            T6.setEnabled(false);
                            T6.addActionListener(actionListenerT6);
                            Panel.add(T6);
                            JButton W6 = new JButton("03pm-04pm");
                            W6.setBounds(650, 170, 100, 80);
                            ActionListener actionListenerW6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "19";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    W6.setEnabled(false);
                                    W6.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            W6.setEnabled(false);
                            W6.addActionListener(actionListenerW6);
                            Panel.add(W6);
                            JButton TH6 = new JButton("03pm-04pm");
                            TH6.setBounds(650, 250, 100, 80);
                            ActionListener actionListenerTH6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "26";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    TH6.setEnabled(false);
                                    TH6.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            TH6.setEnabled(false);
                            TH6.addActionListener(actionListenerTH6);
                            Panel.add(TH6);
                            JButton F6 = new JButton("03pm-04pm");
                            F6.setBounds(650, 330, 100, 80);
                            ActionListener actionListenerF6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "33";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    F6.setEnabled(false);
                                    F6.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            F6.setEnabled(false);
                            F6.addActionListener(actionListenerF6);
                            Panel.add(F6);
                            JButton S6 = new JButton("03pm-04pm");
                            S6.setBounds(650, 410, 100, 80);
                            ActionListener actionListenerS6 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "40";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    S6.setEnabled(false);
                                    S6.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            S6.setEnabled(false);
                            S6.addActionListener(actionListenerS6);
                            Panel.add(S6);
                            // col-7
                            JButton M7 = new JButton("04pm-05pm");
                            M7.setBounds(760, 10, 100, 80);
                            ActionListener actionListenerM7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "6";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    M7.setEnabled(false);
                                    M7.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            M7.setEnabled(false);
                            M7.addActionListener(actionListenerM7);
                            Panel.add(M7);
                            JButton T7 = new JButton("04pm-05pm");
                            T7.setBounds(760, 90, 100, 80);
                            ActionListener actionListenerT7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "13";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    T7.setEnabled(false);
                                    T7.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            T7.setEnabled(false);
                            T7.addActionListener(actionListenerT7);
                            Panel.add(T7);
                            JButton W7 = new JButton("04pm-05pm");
                            W7.setBounds(760, 170, 100, 80);
                            ActionListener actionListenerW7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "20";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    W7.setEnabled(false);
                                    W7.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            W7.setEnabled(false);
                            W7.addActionListener(actionListenerW7);
                            Panel.add(W7);
                            JButton TH7 = new JButton("04pm-05pm");
                            TH7.setBounds(760, 250, 100, 80);
                            ActionListener actionListenerTH7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "27";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    TH7.setEnabled(false);
                                    TH7.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            TH7.setEnabled(false);
                            TH7.addActionListener(actionListenerTH7);
                            Panel.add(TH7);
                            JButton F7 = new JButton("04pm-05pm");
                            F7.setBounds(760, 330, 100, 80);
                            ActionListener actionListenerF7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "34";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    F7.setEnabled(false);
                                    F7.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            F7.setEnabled(false);
                            F7.addActionListener(actionListenerF7);
                            Panel.add(F7);
                            JButton S7 = new JButton("04pm-05pm");
                            S7.setBounds(760, 410, 100, 80);
                            ActionListener actionListenerS7 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "41";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    S7.setEnabled(false);
                                    S7.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            S7.setEnabled(false);
                            S7.addActionListener(actionListenerS7);
                            Panel.add(S7);
                            // col-8
                            JButton M8 = new JButton("05pm-06pm");
                            M8.setBounds(870, 10, 100, 80);
                            ActionListener actionListenerM8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "7";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    M8.setEnabled(false);
                                    M8.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            M8.setEnabled(false);
                            M8.addActionListener(actionListenerM8);
                            Panel.add(M8);
                            JButton T8 = new JButton("05pm-06pm");
                            T8.setBounds(870, 90, 100, 80);
                            ActionListener actionListenerT8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "14";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    T8.setEnabled(false);
                                    T8.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            T8.setEnabled(false);
                            T8.addActionListener(actionListenerT8);
                            Panel.add(T8);
                            JButton W8 = new JButton("05pm-06pm");
                            W8.setBounds(870, 170, 100, 80);
                            ActionListener actionListenerW8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "21";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    W8.setEnabled(false);
                                    W8.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            W8.setEnabled(false);
                            W8.addActionListener(actionListenerW8);
                            Panel.add(W8);
                            JButton TH8 = new JButton("05pm-06pm");
                            TH8.setBounds(870, 250, 100, 80);
                            ActionListener actionListenerTH8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "28";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    TH8.setEnabled(false);
                                    TH8.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            TH8.setEnabled(false);
                            TH8.addActionListener(actionListenerTH8);
                            Panel.add(TH8);
                            JButton F8 = new JButton("05pm-06pm");
                            F8.setBounds(870, 330, 100, 80);
                            ActionListener actionListenerF8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "35";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    F8.setEnabled(false);
                                    F8.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            F8.setEnabled(false);
                            F8.addActionListener(actionListenerF8);
                            Panel.add(F8);

                            JButton S8 = new JButton("05pm-06pm");
                            S8.setBounds(870, 410, 100, 80);
                            ActionListener actionListenerS8 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    time = "42";
                                    sch = "Service is Finished!!";
                                    JOptionPane.showMessageDialog(null, sch, "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                                    S8.setEnabled(false);
                                    S8.setBackground(null);
                                    deleteRow(time);
                                };
                            };
                            S8.setEnabled(false);
                            S8.addActionListener(actionListenerS8);
                            Panel.add(S8);

                            // show Queue to the doctor

                            JButton Show_Q = new JButton("<html><h3>Show_Q</h3></html>");
                            Show_Q.setBounds(870, 500, 100, 40);
                            ActionListener actionListenerShow_Q = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    try {

                                        LocalDate localDate = LocalDate.now();
                                        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
                                        int dayNumber = dayOfWeek.getValue();
                                        LocalTime localIST = LocalTime.now();
                                        int localhour = localIST.getHour();

                                        Class.forName("org.sqlite.JDBC");
                                        Connection con = dbConnect();
                                        Statement stmt = con.createStatement();
                                        String Q = "Select name,phone,schedule from user_data where day_no=" + dayNumber
                                                + " and cur_time=" + localhour;
                                        ResultSet rs = stmt.executeQuery(Q);

                                        // whom to cure
                                        String c1 = (rs.getString(1));
                                        String c2 = (rs.getString(2));
                                        String c3 = (rs.getString(3));
                                        if (c1 == null || c2 == null || c3 == null) {
                                            c1 = "No Appointments in this slot";
                                            c2 = "Patients might be in Walk-in Queue!!";
                                            c3 = "-VNR VJIET GOH";
                                        }
                                        stmt.close();
                                        String next_P = "<html><b>" + c1 + "<br>" + c2 + "<br>" + c3
                                                + "</b></html>";
                                        JOptionPane.showMessageDialog(null, next_P, "Priority based!!",
                                                JOptionPane.INFORMATION_MESSAGE);
                                    } catch (Exception e1) {
                                        JOptionPane.showMessageDialog(null, "Failed to connect Database!",
                                                "VNRVJIET", JOptionPane.ERROR_MESSAGE);
                                        // System.out.println(e1.getMessage());
                                    }
                                };
                            };
                            Show_Q.addActionListener(actionListenerShow_Q);
                            Panel.add(Show_Q);

                            JButton Walk_In = new JButton("<html><h3>Walk-In</h3></html>");
                            Walk_In.setBounds(750, 500, 100, 40);
                            ActionListener actionListenerWalk_In = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    try {
                                        Class.forName("org.sqlite.JDBC");

                                        Connection con = dbConnect();
                                        Statement stmt = con.createStatement();
                                        String Q = "Select name,phone from walk_in";
                                        ResultSet rs = stmt.executeQuery(Q);

                                        // whom to cure
                                        String c1 = (rs.getString(1));
                                        String c2 = (rs.getString(2));
                                        if (c1 == null || c2 == null) {
                                            c1 = "No patients in the Queue.";
                                            c2 = "-VNR VJIET GOH";
                                        }
                                        String next_P = "<html><b>" + c1 + "<br>" + c2
                                                + "</b></html>";
                                        JOptionPane.showMessageDialog(null, next_P, "FCFS!!",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        stmt.close();
                                        if (!c1.equals("No patients in the Queue.")) {

                                            int result = JOptionPane.showConfirmDialog(null, "Do you want to proceed?",
                                                    "Confirmation", JOptionPane.YES_NO_OPTION);
                                            if (result == JOptionPane.YES_OPTION) {
                                                try {
                                                    Class.forName("org.sqlite.JDBC");

                                                    Connection con1 = dbConnect();
                                                    Statement stmt1 = con1.createStatement();

                                                    String Q0 = "select phone from walk_in";
                                                    ResultSet rs1 = stmt1.executeQuery(Q0);
                                                    String mob = rs1.getString(1);

                                                    String Q1 = "delete from walk_in where phone='" + mob + "'";
                                                    stmt1.execute(Q1);
                                                    stmt1.close();
                                                    JOptionPane.showMessageDialog(null, "Service is Finished!!",
                                                            "FCFS!!",
                                                            JOptionPane.INFORMATION_MESSAGE);

                                                } catch (Exception e1) {
                                                    System.out.println(e1.getMessage());
                                                }
                                            }
                                        }

                                    } catch (Exception e1) {
                                        JOptionPane.showMessageDialog(null, "Failed to connect Database!",
                                                "VNRVJIET",
                                                JOptionPane.ERROR_MESSAGE);
                                        System.out.println(e1.getMessage());
                                    }

                                };
                            };
                            Walk_In.addActionListener(actionListenerWalk_In);
                            Panel.add(Walk_In);

                            //
                            // marking booked slots
                            for (int i : slots_over) {
                                if (i == 1) {
                                    M1.setBackground(Color.decode("#EE3300"));
                                    M1.setEnabled(true);
                                } else if (i == 2) {
                                    M2.setBackground(Color.decode("#EE3300"));
                                    M2.setEnabled(true);
                                } else if (i == 3) {
                                    M3.setBackground(Color.decode("#EE3300"));
                                    M3.setEnabled(true);
                                } else if (i == 4) {
                                    M4.setBackground(Color.decode("#EE3300"));
                                    M4.setEnabled(true);
                                } else if (i == 5) {
                                    M6.setBackground(Color.decode("#EE3300"));
                                    M6.setEnabled(true);
                                } else if (i == 6) {
                                    M7.setBackground(Color.decode("#EE3300"));
                                    M7.setEnabled(true);
                                } else if (i == 7) {
                                    M8.setBackground(Color.decode("#EE3300"));
                                    M8.setEnabled(true);
                                } else if (i == 8) {
                                    T1.setBackground(Color.decode("#EE3300"));
                                    T1.setEnabled(true);
                                } else if (i == 9) {
                                    T2.setBackground(Color.decode("#EE3300"));
                                    T2.setEnabled(true);
                                } else if (i == 10) {
                                    T3.setBackground(Color.decode("#EE3300"));
                                    T3.setEnabled(true);
                                } else if (i == 11) {
                                    T4.setBackground(Color.decode("#EE3300"));
                                    T4.setEnabled(true);
                                } else if (i == 12) {
                                    T6.setBackground(Color.decode("#EE3300"));
                                    T6.setEnabled(true);
                                } else if (i == 13) {
                                    T7.setBackground(Color.decode("#EE3300"));
                                    T7.setEnabled(true);
                                } else if (i == 14) {
                                    T8.setBackground(Color.decode("#EE3300"));
                                    T8.setEnabled(true);
                                } else if (i == 15) {
                                    W1.setBackground(Color.decode("#EE3300"));
                                    W1.setEnabled(true);
                                } else if (i == 16) {
                                    W2.setBackground(Color.decode("#EE3300"));
                                    W2.setEnabled(true);
                                } else if (i == 17) {
                                    W3.setBackground(Color.decode("#EE3300"));
                                    W3.setEnabled(true);
                                } else if (i == 18) {
                                    W4.setBackground(Color.decode("#EE3300"));
                                    W4.setEnabled(true);
                                } else if (i == 19) {
                                    W6.setBackground(Color.decode("#EE3300"));
                                    W6.setEnabled(true);
                                } else if (i == 20) {
                                    W7.setBackground(Color.decode("#EE3300"));
                                    W7.setEnabled(true);
                                } else if (i == 21) {
                                    W8.setBackground(Color.decode("#EE3300"));
                                    W8.setEnabled(true);
                                } else if (i == 22) {
                                    TH1.setBackground(Color.decode("#EE3300"));
                                    TH1.setEnabled(true);
                                } else if (i == 23) {
                                    TH2.setBackground(Color.decode("#EE3300"));
                                    TH2.setEnabled(true);
                                } else if (i == 24) {
                                    TH3.setBackground(Color.decode("#EE3300"));
                                    TH3.setEnabled(true);
                                } else if (i == 25) {
                                    TH4.setBackground(Color.decode("#EE3300"));
                                    TH4.setEnabled(true);
                                } else if (i == 26) {
                                    TH6.setBackground(Color.decode("#EE3300"));
                                    TH6.setEnabled(true);
                                } else if (i == 27) {
                                    TH7.setBackground(Color.decode("#EE3300"));
                                    TH7.setEnabled(true);
                                } else if (i == 28) {
                                    TH8.setBackground(Color.decode("#EE3300"));
                                    TH8.setEnabled(true);
                                } else if (i == 29) {
                                    F1.setBackground(Color.decode("#EE3300"));
                                    F1.setEnabled(true);
                                } else if (i == 30) {
                                    F2.setBackground(Color.decode("#EE3300"));
                                    F2.setEnabled(true);
                                } else if (i == 31) {
                                    F3.setBackground(Color.decode("#EE3300"));
                                    F3.setEnabled(true);
                                } else if (i == 32) {
                                    F4.setBackground(Color.decode("#EE3300"));
                                    F4.setEnabled(true);
                                } else if (i == 33) {
                                    F6.setBackground(Color.decode("#EE3300"));
                                    F6.setEnabled(true);
                                } else if (i == 34) {
                                    F7.setBackground(Color.decode("#EE3300"));
                                    F7.setEnabled(true);
                                } else if (i == 35) {
                                    F8.setBackground(Color.decode("#EE3300"));
                                    F8.setEnabled(true);
                                } else if (i == 36) {
                                    S1.setBackground(Color.decode("#EE3300"));
                                    S1.setEnabled(true);
                                } else if (i == 37) {
                                    S2.setBackground(Color.decode("#EE3300"));
                                    S2.setEnabled(false);
                                } else if (i == 38) {
                                    S3.setBackground(Color.decode("#EE3300"));
                                    S3.setEnabled(true);
                                } else if (i == 39) {
                                    S4.setBackground(Color.decode("#EE3300"));
                                    S4.setEnabled(true);
                                } else if (i == 40) {
                                    S6.setBackground(Color.decode("#EE3300"));
                                    S6.setEnabled(true);
                                } else if (i == 41) {
                                    S7.setBackground(Color.decode("#EE3300"));
                                    S7.setEnabled(true);
                                } else if (i == 42) {
                                    S8.setBackground(Color.decode("#EE3300"));
                                    S8.setEnabled(true);
                                }
                                //
                                //
                                Panel.repaint();
                                frame.repaint();
                                frame.revalidate();

                            }
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, "Failed to connect Database!", "VNRVJIET",
                                    JOptionPane.ERROR_MESSAGE);

                        }

                        break;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Incorrect Password</h></b></html>",
                                "VNRVJIET", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        Doctor.addActionListener(actionListenerDoctor);
        frame.add(Doctor);

        LocalDate currentDate = LocalDate.now();
        String sdate = "<html><h2 style='color:grey;'>" + currentDate + "," + "</h2></html>";
        JLabel date_P = new JLabel(sdate);
        date_P.setForeground(new Color(0, 0, 0));
        date_P.setFont(new Font("Comic Sans MS", Font.ROMAN_BASELINE, 20));
        date_P.setBounds(1355, 170, 120, 30);
        frame.add(date_P);

        int dayNumber = currentDate.getDayOfWeek().getValue();
        String day = "";
        if (dayNumber == 1) {
            day = "Monday";
        } else if (dayNumber == 2) {
            day = "Tuesday";
        } else if (dayNumber == 3) {
            day = "Wednesday";
        } else if (dayNumber == 4) {
            day = "Thursday";
        } else if (dayNumber == 5) {
            day = "Friday";
        } else if (dayNumber == 6) {
            day = "Saturday";
        } else {
            day = "Sunday";
        }

        String sday = "<html><h2 style='color:grey;'>" + day + "." + "</h2></html>";
        JLabel day_P = new JLabel(sday);
        day_P.setForeground(new Color(0, 0, 0));
        day_P.setFont(new Font("Comic Sans MS", Font.ROMAN_BASELINE, 20));
        day_P.setBounds(1355, 200, 120, 30);
        frame.add(day_P);

        // common settings for User Interface
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(screenSize);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        try {// execute Batch program for setting classpath
            String fp = "BatchCodeToSetClassPath.bat";
            ProcessBuilder pb = new ProcessBuilder(fp);
            Process p = pb.start();
            int status = p.waitFor();
            if (status == 0)// 0 for Success
            {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null,
                            "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Loading Failed</h></b></html>",
                            "VNRVJIET", JOptionPane.INFORMATION_MESSAGE);
                }

                while (true) {
                    String psw = JOptionPane.showInputDialog(null,
                            "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Enter Password:</h></b></html>",
                            "VNRVJIET", JOptionPane.OK_CANCEL_OPTION);
                    if (psw == null)
                        break;
                    else if (psw.equals("123456")) {
                        JOptionPane.showMessageDialog(null,
                                "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Welcome User!!</h></b></html>",
                                "VNRVJIET", JOptionPane.INFORMATION_MESSAGE);
                        obj = new Main();
                        DaysGone();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Incorrect Password</h></b></html>",
                                "VNRVJIET", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                throw new Exception();
            }
        } catch (Exception errorOccured) {
            JOptionPane.showMessageDialog(null,
                    "<html><b><h4 style='color:grey;font-family:Comic Sans MS;'>Couldn't run this application!</h></b></html>",
                    "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}