from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, relationship


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


OSTest.images = relationship("OSImage", order_by=OSImage.id, back_populates="test")


engine = create_engine('mysql+pymysql://test:test-321@localhost/test', pool_recycle=3600)
Session = sessionmaker(bind=engine)