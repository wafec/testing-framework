from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, ForeignKey, DateTime
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, relationship
import datetime

Base = declarative_base()


class OSTest(Base):
    __tablename__ = 'OS_TEST'

    id = Column(Integer, primary_key=True)
    alias = Column(String)
    username = Column(String)
    password = Column('passwd', String)
    project_name = Column(String)
    user_domain_name = Column(String)
    project_domain_name = Column(String)
    auth_url = Column(String)


class OSFlavor(Base):
    __tablename__ = 'OS_FLAVOR'

    id = Column(Integer, primary_key=True)
    uid = Column(String)
    name = Column(String)
    vcpus = Column(Integer)
    ram = Column(Integer)
    disk = Column(Integer)
    test_id = Column(Integer, ForeignKey('OS_TEST.id'))

    test = relationship("OSTest", back_populates="flavors")

    def __repr__(self):
        return '<OSFlavor(id=%s, uid=%s, name=%s, vcpus=%s, ram=%s, disk=%s, test_id=%s)>' % (
            self.id, self.uid, self.name, self.vcpus, self.ram, self.disk, self.test_id
        )


OSTest.flavors = relationship("OSFlavor", order_by=OSFlavor.id, back_populates="test")


class OSImage(Base):
    __tablename__ = 'OS_IMAGE'

    id = Column(Integer, primary_key=True)
    uid = Column(String)
    name = Column(String)
    disk_format = Column(String)
    container_format = Column(String)
    test_id = Column(Integer, ForeignKey('OS_TEST.id'))

    test = relationship("OSTest", back_populates="images")

    def __repr__(self):
        return '<OSImage(id=%s, uid=%s, name=%s, disk_format=%s, container_format=%s, test_id=%s)>' % (
            self.id, self.uid, self.name, self.disk_format, self.container_format, self.test_id
        )


OSTest.images = relationship("OSImage", order_by=OSImage.id, back_populates="test")


class OSServer(Base):
    __tablename__ = 'OS_SERVER'

    id = Column(Integer, primary_key=True)
    uid = Column(String)
    name = Column(String)
    last_action = Column(String)
    flavor_id = Column(Integer, ForeignKey('OS_FLAVOR.id'))
    image_id = Column(Integer, ForeignKey('OS_IMAGE.id'))
    test_id = Column(Integer, ForeignKey('OS_TEST.id'))
    network_id = Column(Integer, ForeignKey('OS_NETWORK.id'))
    flavor_alt_id = Column(Integer, ForeignKey('OS_FLAVOR.id'))

    test = relationship("OSTest", back_populates="servers")
    image = relationship("OSImage")
    flavor = relationship("OSFlavor", foreign_keys=[flavor_id])
    network = relationship("OSNetwork")
    flavor_alt = relationship("OSFlavor", foreign_keys=[flavor_alt_id])

    def __repr__(self):
        return '<OSServer(id=%s, uid=%s, name=%s, flavor_id=%s, image_id=%s, test_id=%s, network_id=%s, ' \
               'last_action=%s)>' % (
                   self.id, self.uid, self.name, self.flavor_id, self.image_id, self.test_id, self.network_id,
                   self.last_action
               )


OSTest.servers = relationship("OSServer", order_by=OSServer.id, back_populates="test")


class OSNetwork(Base):
    __tablename__ = 'OS_NETWORK'

    id = Column(Integer, primary_key=True)
    uid = Column(String)
    name = Column(String)
    project_uid = Column(String)
    test_id = Column(Integer, ForeignKey('OS_TEST.id'))

    test = relationship("OSTest", back_populates="networks")

    def __repr__(self):
        return '<OSNetwork(id=%s, uid=%s, name=%s, project_uid=%s, test_id=%s)>' % (
            self.id, self.uid, self.name, self.project_uid, self.test_id
        )


OSTest.networks = relationship("OSNetwork", order_by=OSNetwork.id, back_populates="test")


class OSTestLog(Base):
    __tablename__ = 'OS_TEST_LOG'

    id = Column(Integer, primary_key=True)
    test_id = Column(Integer, ForeignKey('OS_TEST.id'))
    log = Column(String)
    log_date = Column(DateTime, default=datetime.datetime.now)

    test = relationship("OSTest", back_populates="logs")


OSTest.logs = relationship("OSTestLog", order_by=OSTestLog.id, back_populates="test")

engine = create_engine('mysql+pymysql://test:test-321@localhost/test', pool_recycle=3600)
Session = sessionmaker(bind=engine)


class OSVolume(Base):
    __tablename__ = 'OS_VOLUME'

    id = Column(Integer, primary_key=True)
    uid = Column(String)
    test_id = Column(Integer, ForeignKey('OS_TEST.id'))
    name = Column(String)
    availability_zone = Column(String)
    size = Column(Integer)
    status = Column(String)

    test = relationship('OSTest', back_populates='volumes')

    def __repr__(self):
        return '<OSVolume(id=%s, uid=%s, test_id=%s, name=%s, zone=%s, size=%s, status=%s)>' % (
            self.id, self.uid, self.test_id, self.name, self.availability_zone, self.size, self.status
        )


OSTest.volumes = relationship("OSVolume", order_by=OSVolume.id, back_populates="test")