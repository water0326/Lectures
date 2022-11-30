package Class_Register;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

import Class_Register.Canvas;

public class Canvas extends JFrame{

	// Screen Component //
	static Canvas prg = new Canvas();
	
	static int height = 800;
	static float screenRatio = 18f / 9f;
	static int width = (int)(height * screenRatio);
	
	// Panel Component //
	static Container home;
	static Container register;
	static Container check;
	static Container cancel;
	
	// DataBase //
	static String[] prof_name = {"A교수", "B교수", "C교수"};
	static String[] stud_name = {"S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10"};
	static String[] subject_name = {"객체지향 언어", "자료구조", "운영체재"};
	static String[] room_name = {"혜화관 1호", "혜화관 2호", "명진관 1호", "명진관 2호", "학림관 1호", "학림관 2호"};
	
	static Professor[] profs = new Professor[prof_name.length];
	static Student[] students = new Student[stud_name.length];
	static Lecture[] lectures = new Lecture[subject_name.length];
	static Lecture_Room[] rooms = new Lecture_Room[room_name.length];
	
	// Fixed Variable //
	final static int LectureCount = subject_name.length;
	
	static int studentIdx = -1;
	
	static Map<String, String[]> DataBase = new HashMap<>() {
		{
			put("Professor", prof_name);
			put("Student", stud_name);
			put("Subject", subject_name);
			put("Room", room_name);
		}
	};
	
	//static String[] key_list = DataBase.keySet().toArray(new String[0]);
	
	static void HomeScreen() {
		
		home = prg.createRootPane();
		home.setLayout(null);
		
		JLabel home_title = new JLabel("수강 신청 시스템", JLabel.CENTER);
		JButton[] home_button = new JButton[2];
		
		home_title.setText("수강 신청 시스템");
		home_title.setSize(width, 50);
		home_title.setLocation(0, height / 4);
		home_title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		
		int button_width = 300;
		int button_height = 42;
		
		home_button[0] = new JButton("수강 신청");
		home_button[1] = new JButton("수강 조회");
		for(int idx = 0 ; idx < home_button.length ; idx++) {
			home_button[idx].setBackground(Color.white);
			home_button[idx].setSize(button_width, button_height);
			home_button[idx].setLocation(width / 2 - button_width / 2, height / 2 + (button_height + 10) * idx);
			home_button[idx].setFont(new Font("맑은 고딕", Font.BOLD, 30));
		}
		
		home_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterScreen();
			}
		});
		home_button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckScreen();
			}
		});
		
		
		// Add Component //
		home.add(home_title);
		for(int idx = 0 ; idx < home_button.length ; idx++) {
			home.add(home_button[idx]);
		}
		
		prg.setContentPane(home);
		prg.revalidate();
		
	}
	static void RegisterScreen() {
		
		
		
		register = prg.createRootPane();
		register.setLayout(null);
		
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		JLabel register_title = new JLabel("수강 신청", JLabel.CENTER);
		JButton register_button = new JButton("돌아가기");
		JLabel studentInput_label = new JLabel("이름 : ", JLabel.CENTER);
		JTextField studentInput = new JTextField(20);
		JButton studentInput_checkButton = new JButton("확인");
		JLabel currentStatus = new JLabel("수강신청 화면 입니다", JLabel.CENTER);
		
		int view_count = 4;
		int content_height = 50;
		int content_width_margin = 200;
		int content_height_margin = 20;
		
		JLabel[][] lectureList = new JLabel[LectureCount][view_count];
		JButton[] lectureApplyButton = new JButton[LectureCount];
		
		
		for(int lectureIdx = 0 ; lectureIdx < LectureCount ; lectureIdx++) {
			lectureList[lectureIdx][0] = new JLabel(lectures[lectureIdx].subject, JLabel.CENTER);
			lectureList[lectureIdx][1] = new JLabel(lectures[lectureIdx].prof.name, JLabel.CENTER);
			lectureList[lectureIdx][2] = new JLabel(lectures[lectureIdx].room.room, JLabel.CENTER);
			lectureList[lectureIdx][3] = new JLabel(lectures[lectureIdx].time, JLabel.CENTER);
			
			lectureApplyButton[lectureIdx] = new JButton("");
			lectureApplyButton[lectureIdx].setBackground(Color.gray);
			lectureApplyButton[lectureIdx].setSize(content_width_margin / 2, content_height);
			lectureApplyButton[lectureIdx].setLocation(width - (int)(content_width_margin * 2.5 / 2), height / 3 + (content_height + content_height_margin) * lectureIdx + content_height_margin / 2);
			lectureApplyButton[lectureIdx].setFont(new Font("맑은 고딕", Font.BOLD, 30));
			
			for(int idx = 0 ; idx < view_count ; idx++) {
				lectureList[lectureIdx][idx].setSize((width - 2 * content_width_margin) / view_count, content_height + content_height_margin);
				lectureList[lectureIdx][idx].setLocation((width - 2 * content_width_margin) / view_count * idx + content_width_margin / 2, height / 3 + (content_height + content_height_margin) * lectureIdx);
				lectureList[lectureIdx][idx].setFont(new Font("맑은 고딕", Font.BOLD, 30));
				lectureList[lectureIdx][idx].setBackground(Color.white);
				lectureList[lectureIdx][idx].setOpaque(true);
				lectureList[lectureIdx][idx].setBorder(blackline);
				
			}
		}
		
		lectureApplyButton[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(studentIdx == -1) return;
					
				int idx = 0;
				
				students[studentIdx].changeRegister(idx);
				currentStatus.setText(lectures[idx].subject + " 과목 수강이 " + lectureApplyButton[idx].getText() + "되었습니다.");
				if(students[studentIdx].isregistered[idx]) {
					lectureApplyButton[idx].setText("취소");
				}
				else {
					lectureApplyButton[idx].setText("신청");
				}
				
			}
		});
		lectureApplyButton[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(studentIdx == -1) return;
				
				int idx = 1;
				
				students[studentIdx].changeRegister(idx);
				currentStatus.setText(lectures[idx].subject + " 과목 수강이 " + lectureApplyButton[idx].getText() + "되었습니다.");
				if(students[studentIdx].isregistered[idx]) {
					lectureApplyButton[idx].setText("취소");
				}
				else {
					lectureApplyButton[idx].setText("신청");
				}
				
			}
		});
		lectureApplyButton[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(studentIdx == -1) return;
				
				int idx = 2;
				
				students[studentIdx].changeRegister(idx);
				currentStatus.setText(lectures[idx].subject + " 과목 수강이 " + lectureApplyButton[idx].getText() + "되었습니다.");
				if(students[studentIdx].isregistered[idx]) {
					lectureApplyButton[idx].setText("취소");
				}
				else {
					lectureApplyButton[idx].setText("신청");
				}
				
			}
		});
		
		register_title.setSize(width, 50);
		register_title.setLocation(0, height / 10);
		register_title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		
		studentInput_label.setSize(width / 3, 50);
		studentInput_label.setLocation(0, height / 5);
		studentInput_label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		
		studentInput.setSize(width / 2, 50);
		studentInput.setLocation(width / 3, height / 5);
		studentInput.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		
		currentStatus.setSize(width / 5 + 50, 60);
		currentStatus.setLocation(100, (int)(height / 1.3f));
		currentStatus.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		currentStatus.setBackground(Color.white);
		currentStatus.setBorder(blackline);
		currentStatus.setOpaque(true);
		
		studentInput_checkButton.setBackground(Color.white);
		studentInput_checkButton.setSize(70, 50);
		studentInput_checkButton.setLocation(width - 250, height / 5);
		studentInput.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		
		studentInput_checkButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				studentIdx = -1;
				// TODO Auto-generated method stub
				for(int idx = 0 ; idx < students.length ; idx++) {
					if(students[idx].name.equals(studentInput.getText())) {
						studentIdx = idx;
						break;
					}
				}
				if(studentIdx != -1) {
					// correct
					currentStatus.setText("'" + students[studentIdx].name + "' 학생의 정보를 불러왔습니다.");
					for(int lectureIdx = 0 ; lectureIdx < LectureCount ; lectureIdx++) {
						lectureApplyButton[lectureIdx].setBackground(Color.white);
						if(students[studentIdx].isregistered[lectureIdx]) {
							lectureApplyButton[lectureIdx].setText("취소");
						}
						else {
							lectureApplyButton[lectureIdx].setText("신청");
						}
					}
				}
				else {
					// error
					currentStatus.setText("존재하지 않는 학생 이름 입니다.");
					for(int lectureIdx = 0 ; lectureIdx < LectureCount ; lectureIdx++) {
						lectureApplyButton[lectureIdx].setBackground(Color.gray);
						lectureApplyButton[lectureIdx].setText("");
					}
				}
			}
		});
		
		int button_width = 300;
		int button_height = 42;
		
		register_button = new JButton("돌아가기");
		register_button.setBackground(Color.white);
		register_button.setSize(button_width, button_height);
		register_button.setLocation(width / 2 - button_width / 2, (int)(height / 1.3f));
		register_button.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		register_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HomeScreen();
			}
		});
		
		register.add(register_title);
		register.add(register_button);
		register.add(studentInput_label);
		register.add(studentInput);
		register.add(studentInput_checkButton);
		register.add(currentStatus);
		for(int lectureIdx = 0 ; lectureIdx < lectureList.length ; lectureIdx++) {
			for(int idx = 0 ; idx < view_count ; idx++) {
				register.add(lectureList[lectureIdx][idx]);
			}
			register.add(lectureApplyButton[lectureIdx]);
		}
		
		
		prg.setContentPane(register);
		prg.revalidate();
		
	}
	static void CheckScreen() {
		
		check = prg.createRootPane();
		check.setLayout(null);
		
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		int scrollMargin = 100;
		
		//JPanel scroll_view_parent = new JPanel();
		//scroll_view_parent.setLayout(null);
		
		int view_table_count = 50;
		
		JPanel scroll_view = new JPanel();
		scroll_view.setLayout(null);
		scroll_view.setPreferredSize(new Dimension(width - 4 * scrollMargin, 30 * view_table_count));
		
		JLabel[][] view_table = new JLabel[view_table_count][5];
		
		for(int i = 0 ; i < view_table_count ; i++) {
			for(int j = 0 ; j < view_table[0].length ; j++) {
				view_table[i][j] = new JLabel("", JLabel.CENTER);
				view_table[i][j].setSize((width - 2 * scrollMargin) / 5, 30);
				view_table[i][j].setLocation(((width - 2 * scrollMargin) / 5) * j, 30 * i);
				view_table[i][j].setFont(new Font("맑은 고딕", Font.BOLD, 20));
				view_table[i][j].setBorder(blackline);
				view_table[i][j].setBackground(Color.white);
				view_table[i][j].setOpaque(true);
				scroll_view.add(view_table[i][j]);
			}
		}
		
		//scroll_view_parent.add(scroll_view);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(scroll_view);
		scroll.setSize(width - 2 * scrollMargin, 300);
		scroll.setLocation(scrollMargin, (int)(height / 2.5));
		
		JLabel check_title = new JLabel("수강 조회", JLabel.CENTER);
		
		int viewPointCount = 4;
		int viewButtonWidth = 250;
		int viewButtonHeight = 50;
		int viewButtonMargin = 50;
		
		JButton check_button = new JButton("돌아가기");
		
		viewPoint[] view = new viewPoint[viewPointCount];
		view[0] = new viewPoint(new JButton("학생 View"), "학생 View", 5);
		view[1] = new viewPoint(new JButton("교수 View"), "교수 View", 4);
		view[2] = new viewPoint(new JButton("강의실 View"), "강의실 View", 4);
		view[3] = new viewPoint(new JButton("과목 View"), "과목 View", 4);
		
		for(int idx = 0 ; idx < viewPointCount ; idx++) {
			view[idx].btn.setSize(viewButtonWidth, viewButtonHeight);
			view[idx].btn.setLocation(viewButtonMargin + (width - 2 * viewButtonMargin) / 4 * idx + ((width - 2 * viewButtonMargin) / 4 - viewButtonWidth) / 2, height / 4);
			view[idx].btn.setFont(new Font("맑은 고딕", Font.BOLD, 30));
			view[idx].btn.setBackground(Color.white);
		}
		
		view[0].btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				view_table[0][0].setText("학생 이름");
				view_table[0][1].setText("과목");
				view_table[0][2].setText("교수 이름");
				view_table[0][3].setText("시간");
				view_table[0][4].setText("강의실");
				
				int listIdx = 1;
				
				for(int i = 0 ; i < students.length ; i++) {
					for(int j = 0 ; j < LectureCount ; j++) {
						if(students[i].isregistered[j]) {
							view_table[listIdx][0].setText(students[i].name);
							view_table[listIdx][1].setText(lectures[j].subject);
							view_table[listIdx][2].setText(lectures[j].prof.name);
							view_table[listIdx][3].setText(lectures[j].time);
							view_table[listIdx][4].setText(lectures[j].room.room);
							listIdx++;
						}
					}
				}
				for(int i = listIdx ; i < view_table_count ; i++) {
					for(int j = 0 ; j < view_table[0].length ; j++) {
						view_table[i][j].setText("");
					}
				}
				
			}
		});
		
		view[1].btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				view_table[0][0].setText("교수 이름");
				view_table[0][1].setText("과목");
				view_table[0][2].setText("강의실");
				view_table[0][3].setText("시간");
				view_table[0][4].setText("");
				
				int listIdx = 1;
				
				for(int i = 0 ; i < profs.length ; i++) {
					for(int j = 0 ; j < LectureCount ; j++) {
						if(profs[i] == lectures[j].prof) {
							view_table[listIdx][0].setText(profs[i].name);
							view_table[listIdx][1].setText(lectures[j].subject);
							view_table[listIdx][2].setText(lectures[j].room.room);
							view_table[listIdx][3].setText(lectures[j].time);
							view_table[listIdx][4].setText("");
							listIdx++;
						}
					}
				}
				for(int i = listIdx ; i < view_table_count ; i++) {
					for(int j = 0 ; j < view_table[0].length ; j++) {
						view_table[i][j].setText("");
					}
				}
				
			}
		});
		
		view[2].btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				view_table[0][0].setText("강의실");
				view_table[0][1].setText("시간");
				view_table[0][2].setText("교수 이름");
				view_table[0][3].setText("과목");
				view_table[0][4].setText("");
				
				int listIdx = 1;
				
				for(int i = 0 ; i < rooms.length ; i++) {
					for(int j = 0 ; j < LectureCount ; j++) {
						if(rooms[i] == lectures[j].room) {
							view_table[listIdx][0].setText(rooms[i].room);
							view_table[listIdx][1].setText(lectures[j].time);
							view_table[listIdx][2].setText(lectures[j].prof.name);
							view_table[listIdx][3].setText(lectures[j].subject);
							view_table[listIdx][4].setText("");
							listIdx++;
						}
					}
				}
				for(int i = listIdx ; i < view_table_count ; i++) {
					for(int j = 0 ; j < view_table[0].length ; j++) {
						view_table[i][j].setText("");
					}
				}
				
			}
		});
		
		view[3].btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				view_table[0][0].setText("과목");
				view_table[0][1].setText("교수 이름");
				view_table[0][2].setText("강의실");
				view_table[0][3].setText("시간");
				view_table[0][4].setText("");
				
				int listIdx = 1;
				
				for(int i = 0 ; i < LectureCount ; i++) {
					view_table[listIdx][0].setText(lectures[i].subject);
					view_table[listIdx][1].setText(lectures[i].prof.name);
					view_table[listIdx][2].setText(lectures[i].room.room);
					view_table[listIdx][3].setText(lectures[i].time);
					view_table[listIdx][4].setText("");
					listIdx++;
				}
				for(int i = listIdx ; i < view_table_count ; i++) {
					for(int j = 0 ; j < view_table[0].length ; j++) {
						view_table[i][j].setText("");
					}
				}
				
			}
		});
		
		check_title.setSize(width, 50);
		check_title.setLocation(0, height / 10);
		check_title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		
		int button_width = 300;
		int button_height = 42;
		
		check_button = new JButton("돌아가기");
		check_button.setBackground(Color.white);
		check_button.setSize(button_width, button_height);
		check_button.setLocation(width / 2 - button_width / 2, (int)(height / 1.2f));
		check_button.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		check_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HomeScreen();
			}
		});
		
		
		
		check.add(check_title);
		for(int idx = 0 ; idx < viewPointCount ; idx++) {
			check.add(view[idx].btn);
		}
		check.add(scroll);
		check.add(check_button);
		
		prg.setContentPane(check);
		prg.revalidate();
		
	}
	
	
	static void CreateClass() {
		
		String[] obj;
		
		obj = DataBase.get("Professor");
		for(int idx = 0 ; idx < obj.length ; idx++) {
			profs[idx] = new Professor(obj[idx]);
		}
		
		obj = DataBase.get("Room");
		for(int idx = 0 ; idx < obj.length ; idx++) {
			rooms[idx] = new Lecture_Room(obj[idx]);
		}
		
		obj = DataBase.get("Student");
		for(int idx = 0 ; idx < obj.length ; idx++) {
			students[idx] = new Student(obj[idx]);
		}
		
		obj = DataBase.get("Subject");
		
		lectures[0] = new Lecture(obj[0], profs[0], rooms[0], "수요일 1,2,3교시");
		lectures[1] = new Lecture(obj[1], profs[1], rooms[1], "금요일 4,5,6교시");
		lectures[2] = new Lecture(obj[2], profs[2], rooms[2], "월요일 5,6,7교시");
		
	}

	
	public static void main(String[] args) {
	
		CreateClass();
		
		prg.setTitle("수강신청 시스템");
		prg.setDefaultCloseOperation(EXIT_ON_CLOSE);
		prg.setSize(width, height);
		prg.setVisible(true);
		
		HomeScreen();
		
		
	
	}
}

class viewPoint {
	JButton btn;
	String name;
	int listCount;
	public viewPoint(JButton btn, String name, int listCount) {
		this.btn = btn;
		this.name = name;
		this.listCount = listCount;
	}
}

class Lecture{
	String subject;
	Professor prof;
	Lecture_Room room;
	String time;
	
	Lecture(String subject, Professor prof, Lecture_Room room, String time) {
		this.subject = subject;
		this.prof = prof;
		this.room = room;
		this.time = time;
	}
}

class Professor {
	String name;
	
	Professor(String name) {
		this.name = name;
	}
}

class Lecture_Room {
	String room;
	Lecture_Room(String room) {
		this.room = room;
	}
}

class Student {
	private static final int LectureCount = 3;
	String name;
	Boolean[] isregistered = new Boolean[LectureCount];
	Student(String name) {
		this.name = name;
		for(int idx = 0 ; idx < LectureCount ; idx++) {
			isregistered[idx] = false;
		}
		
	}
	void changeRegister(int idx) {
		isregistered[idx] = !isregistered[idx];
	}
	
}