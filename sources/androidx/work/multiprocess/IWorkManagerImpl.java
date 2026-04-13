package androidx.work.multiprocess;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.work.multiprocess.IWorkManagerImplCallback;

public interface IWorkManagerImpl extends IInterface {
    void cancelAllWork(IWorkManagerImplCallback iWorkManagerImplCallback);

    void cancelAllWorkByTag(String str, IWorkManagerImplCallback iWorkManagerImplCallback);

    void cancelUniqueWork(String str, IWorkManagerImplCallback iWorkManagerImplCallback);

    void cancelWorkById(String str, IWorkManagerImplCallback iWorkManagerImplCallback);

    void enqueueContinuation(byte[] bArr, IWorkManagerImplCallback iWorkManagerImplCallback);

    void enqueueWorkRequests(byte[] bArr, IWorkManagerImplCallback iWorkManagerImplCallback);

    void queryWorkInfo(byte[] bArr, IWorkManagerImplCallback iWorkManagerImplCallback);

    void setProgress(byte[] bArr, IWorkManagerImplCallback iWorkManagerImplCallback);

    public static class Default implements IWorkManagerImpl {
        public void enqueueWorkRequests(byte[] request, IWorkManagerImplCallback callback) {
        }

        public void enqueueContinuation(byte[] request, IWorkManagerImplCallback callback) {
        }

        public void cancelWorkById(String id, IWorkManagerImplCallback callback) {
        }

        public void cancelAllWorkByTag(String tag, IWorkManagerImplCallback callback) {
        }

        public void cancelUniqueWork(String name, IWorkManagerImplCallback callback) {
        }

        public void cancelAllWork(IWorkManagerImplCallback callback) {
        }

        public void queryWorkInfo(byte[] request, IWorkManagerImplCallback callback) {
        }

        public void setProgress(byte[] request, IWorkManagerImplCallback callback) {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWorkManagerImpl {
        private static final String DESCRIPTOR = "androidx.work.multiprocess.IWorkManagerImpl";
        static final int TRANSACTION_cancelAllWork = 6;
        static final int TRANSACTION_cancelAllWorkByTag = 4;
        static final int TRANSACTION_cancelUniqueWork = 5;
        static final int TRANSACTION_cancelWorkById = 3;
        static final int TRANSACTION_enqueueContinuation = 2;
        static final int TRANSACTION_enqueueWorkRequests = 1;
        static final int TRANSACTION_queryWorkInfo = 7;
        static final int TRANSACTION_setProgress = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWorkManagerImpl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IWorkManagerImpl)) {
                return new Proxy(obj);
            }
            return (IWorkManagerImpl) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    enqueueWorkRequests(data.createByteArray(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    enqueueContinuation(data.createByteArray(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    cancelWorkById(data.readString(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    cancelAllWorkByTag(data.readString(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    cancelUniqueWork(data.readString(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    cancelAllWork(IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    queryWorkInfo(data.createByteArray(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    setProgress(data.createByteArray(), IWorkManagerImplCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        public static class Proxy implements IWorkManagerImpl {
            public static IWorkManagerImpl sDefaultImpl;
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

            public void enqueueWorkRequests(byte[] request, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(request);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().enqueueWorkRequests(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void enqueueContinuation(byte[] request, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(request);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().enqueueContinuation(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelWorkById(String id, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelWorkById(id, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelAllWorkByTag(String tag, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelAllWorkByTag(tag, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelUniqueWork(String name, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelUniqueWork(name, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelAllWork(IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelAllWork(callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void queryWorkInfo(byte[] request, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(request);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().queryWorkInfo(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setProgress(byte[] request, IWorkManagerImplCallback callback) {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(request);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setProgress(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWorkManagerImpl impl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (impl == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = impl;
                return true;
            }
        }

        public static IWorkManagerImpl getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
