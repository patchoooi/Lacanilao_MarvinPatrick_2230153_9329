/**
 AUTHOR: Lacanilao, Marvin Patrick D.
 ACITIVITY: Midterm Exercise 1
 CLASS CODE: 9329 - CS 222L Computer Programming 3
 */

package mexer1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MidInterface1 extends Remote {

    String profileString(String s) throws RemoteException;
}
