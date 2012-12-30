package com.raimsoft.spacetrader.obj.fleets;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.raimsoft.spacetrader.R;

import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

import android.content.Context;

public class TraningShip2 extends BaseFleet
{
	private static int MAX_PARTICLE = 20;
	private Random MyRand = new Random();
	


	Sprite sprGlow;
	private GameObject Particle1[] = new GameObject[MAX_PARTICLE];
	private GameObject Particle2[] = new GameObject[MAX_PARTICLE];
	private GameObject Particle3[] = new GameObject[MAX_PARTICLE];
	
	public TraningShip2(GL10 _gl, Context _context)
	{
		super(_gl, _context);
		
		fVelocity= 13.0f;
		fHandeling= 2.0f;
		nHP= 3500;
		
		sprGlow= new Sprite();
		sprGlow.LoadSprite( _gl, _context, R.drawable.glow, "glow.spr" );
		
		for ( int i = 0; i < MAX_PARTICLE; i++ ) Particle1[i] = new GameObject();
		for ( int i = 0; i < MAX_PARTICLE; i++ ) Particle2[i] = new GameObject();
		for ( int i = 0; i < MAX_PARTICLE; i++ ) Particle3[i] = new GameObject();
	}


	private void SetGlowParticle()
	{
		int cnt1= 0, cnt2=0;		

		for ( int i = 0; i < MAX_PARTICLE; i++ )
		{
			if ( Particle1[i].dead == true )
			{
				Particle1[i].SetObject( sprGlow, 0, 0, x-9, y+45, 0, 7 );	// 위치
				Particle1[i].dead = false;
				Particle1[i].angle= 180;						// 각도
				Particle1[i].angle -= MyRand.nextInt(14)-7;	// 각도범위
				Particle1[i].speed = 6f + (MyRand.nextInt(5) * 0.5f);	// 나가는속도
				Particle1[i].effect = 1;
				Particle1[i].trans= 0.5f;	// 투명시작
				Particle1[i].scalex= 0.3f;
				Particle1[i].scaley= 0.4f;
				if ( ++cnt1 == 2 ) break;
			}
		}
		for ( int i = 0; i < MAX_PARTICLE; i++ )
		{
			if ( Particle2[i].dead == true )
			{
				Particle2[i].SetObject( sprGlow, 0, 0, x+9, y+45, 0, 7 );	// 위치
				Particle2[i].dead = false;
				Particle2[i].angle= 180;						// 각도
				Particle2[i].angle -= MyRand.nextInt(14)-7;	// 각도범위
				Particle2[i].speed = 6f + (MyRand.nextInt(5) * 0.5f);	// 나가는속도
				Particle2[i].effect = 1;
				Particle2[i].trans= 0.5f;	// 투명시작
				Particle2[i].scalex= 0.3f;
				Particle2[i].scaley= 0.4f;
				if ( ++cnt2 == 2 ) break;
			}
		}
	}
	
	
	
	@Override
	public void DrawSprite(GameInfo info)
	{
		super.DrawSprite(info);
		
		SetGlowParticle();
		
		
		for ( int i = 0; i < MAX_PARTICLE; i++ )
		{
			if ( Particle1[i].dead == false )
			{
				Particle1[i].Zoom( info, 0.03f, 0.03f );				
				Particle1[i].trans -= 0.05f;
				if ( Particle1[i].trans <= 0 ) Particle1[i].dead = true;				
				Particle1[i].MovebyAngle( info, Particle1[i].angle, Particle1[i].speed );
			}
		}
		for ( int i = 0; i < MAX_PARTICLE; i++ )
		{
			if ( Particle2[i].dead == false )
			{
				Particle2[i].Zoom( info, 0.03f, 0.03f );				
				Particle2[i].trans -= 0.05f;
				if ( Particle2[i].trans <= 0 ) Particle2[i].dead = true;				
				Particle2[i].MovebyAngle( info, Particle2[i].angle, Particle2[i].speed );
			}
		}
//		for ( int i = 0; i < MAX_PARTICLE; i++ )
//		{
//			if ( Particle3[i].dead == false )
//			{
//				Particle3[i].Zoom( info, 0.03f, 0.03f );				
//				Particle3[i].trans -= 0.05f;
//				if ( Particle3[i].trans <= 0 ) Particle3[i].dead = true;				
//				Particle3[i].MovebyAngle( info, Particle3[i].angle, Particle3[i].speed );
//			}
//		}
		
		for ( int i = 0; i < MAX_PARTICLE; i++ )
		{
			//if ( Particle0[i].dead == false ) Particle0[i].DrawSprite( info );
			if ( Particle1[i].dead == false ) Particle1[i].DrawSprite( info );
			if ( Particle2[i].dead == false ) Particle2[i].DrawSprite( info );
			//if ( Particle3[i].dead == false ) Particle3[i].DrawSprite( info );
		}
		
		super.DrawSprite(info);
	}


	/**
	 * @return the fHandeling
	 */
	public float getHandeling()
	{
		return fHandeling;
	}



	/**
	 * @param fHandeling the fHandeling to set
	 */
	public void setHandeling(float fHandeling)
	{
		this.fHandeling = fHandeling;
	}


	/**
	 * @return the fVelocity
	 */
	public float GetVelocity()
	{
		return fVelocity;
	}


	@Override
	public void Release() 
	{
		super.Release();
		sprGlow.Release();
	}


	/**
	 * @param fVelocity the fVelocity to set
	 */
	public void SetVelocity(float fVelocity)
	{
		this.fVelocity = fVelocity;
	}

}
