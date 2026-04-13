package androidx.work.multiprocess;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.work.multiprocess.IWorkManagerImplCallback;

public interface IListenableWorkerImpl extends IInterface {
    void interrupt(byte[] bArr, IWorkManagerImplCallback iWorkManagerImplCallback);

    void startWork(byte[] bArr, IWorkManagerImplCallback iWorkManagerImplCallback);

    public static class Default implements IListenableWorkerImpl {
        public void startWork(byte[] request, IWorkManagerImplCallback callback) {
        }

        public void interrupt(byte[] request, IWorkManagerImplCallback callback) {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IListenableWorkerImpl {
        private static final String DESCRIPTOR = "androidx.work.multiprocess.IListenableWorkerImpl";
        static final int TRANSACTION_interrupt = 2;
        static final int TRANSACTION_startWork = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IListenableWorkerImpl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IListenableWorkerImpl)) {
                return new Proxy(obj);
            }
            return (IListenableWorkerImpl) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    startWork(data.createByteArray(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    interrupt(data.createByteArray(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        public static class Proxy implements IListenableWorkerImpl {
            public static IListenableWorkerImpl sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void startWork(byte[] request, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(request);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startWork(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void interrupt(byte[] request, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(request);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().interrupt(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IListenableWorkerImpl impl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (impl == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = impl;
                return true;
            }
        }

        public static IListenableWorkerImpl getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
