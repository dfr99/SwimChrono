from sqlalchemy import Column, Integer, String, Boolean
from sqlalchemy.orm import declarative_base
from sqlalchemy import create_engine
from sqlalchemy.orm import Session
from fastapi import FastAPI

# declarative base class
Base = declarative_base()

# an example mapping using the base
class User(Base):
    __tablename__ = "user"
   
    id = Column(Integer, primary_key=True)
    name = Column(String)
    boolean = Column(Boolean, default=False)


engine = create_engine("sqlite:///swimchrono.db", echo=True, future=True)
Base.metadata.create_all(engine)
session = Session(engine)

app = FastAPI()

@app.get("/")
async def get_all_users():
    user_query = session.query(User)
    return user_query.all()

@app.post("/create")
async def create_user(name: str, boolean: bool):
    user = User(name=name, boolean=boolean)
    session.add(user)
    session.commit()
    return {"user added": user.id}
